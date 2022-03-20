new Vue({
    el: '#app',
    data: {
        optionId: '',
        title: '',
        price: 0,
        connectionCost: 0,
        allIncOptions: [],
        allReqOptions: [],
        myIncOptions: [],
        myReqOptions: [],
        isValid: true,
        finalOption: {
            id:0, title:'', price:0, connectionCost:0, contracts:[],
            plans:[], incompatibleOptions:[], incompatibleOptionsMirror:[],
            requiredOptions:[], requiredOptionsMirror:[]
        }
    },
    watch: {
        myReqOptions: function () {
            this.isValid = this.checkIsValid();
            console.log(this.isValid);
        },
        myIncOptions: function () {
            this.isValid = this.checkIsValid();
            console.log(this.isValid);
        }
    },
    async created() {
        var url = window.location.href.split('/');
        this.optionId = url[4];
        var option;
        axios
            .get('http://localhost:8080/getOption/' + this.optionId)
            .then(response => (option = response.data));
        await axios
            .get('http://localhost:8080/incompatibleOptions/' + this.optionId)
            .then(response => (this.myIncOptions = response.data));
        await axios
            .get('http://localhost:8080/requiredOptions/' + this.optionId)
            .then(response => (this.myReqOptions = response.data));
        await axios
            .get('http://localhost:8080/allOptions')
            .then(response => (this.allIncOptions = response.data));
        Object.assign(this.allReqOptions, this.allIncOptions);
        this.removeOptions(this.allIncOptions, this.myIncOptions);
        this.removeOptions(this.allReqOptions, this.myReqOptions);
        this.title = option.title;
        this.price = option.price;
        this.connectionCost = option.connectionCost;
    },
    methods: {
        async deleteOption() {
            console.log("DELETE!");
            if (confirm("Do you really want to delete this option? ")) {
                await axios.delete('http://localhost:8080/options/' + this.optionId);
                window.location = 'http://localhost:8080/options'
                //location.reload();
            }
        },
        addSetToParam(param, set, key) {
            if (set.length === 0) {
                param.append(key, 'empty');
            } else if (set.length === 1) {
                param.append(key, JSON.stringify(set[0]));
            }
            for (var i = 0; i < set.length; i++) {
                param.append(key, JSON.stringify(set[i]));
            }
        },
        post() {
            let param = new URLSearchParams();
            this.finalOption.id = this.optionId;
            this.finalOption.title = this.title;
            this.finalOption.price = this.price;
            this.finalOption.connectionCost = this.connectionCost;
            this.finalOption.requiredOptions = this.myReqOptions;
            param.append('option', JSON.stringify(this.finalOption));
            this.addSetToParam(param, this.myReqOptions, 'reqOptions')
            this.addSetToParam(param, this.myIncOptions, 'incOptions')
            axios({
                method: 'post',
                url: 'http://localhost:8080/options/update',
                data: param
            }).then(response => (console.log(response))).catch(err => {
                if (err.response) {
                    console.log(err);
                    console.log(err.response.status);
                } else if (err.request) {
                    console.log(err);
                } else {
                    console.log(err);
                }
            });

        },
        checkIsValid() {
            if (this.myIncOptions === [] || this.myReqOptions === [])
                return true;
            for (var i = 0; i < this.myIncOptions.length; i ++) {
                for (var j = 0; j < this.myReqOptions.length; j ++) {
                    if (this.myReqOptions[j].id === this.myIncOptions[i].id)
                        return false;
                }
            }
            return true;
        },
        removeOptions(source, remove) {

            for (var i = 0; i < source.length; i++) {
                console.log('i = ' + i);
                if (source[i].id === parseInt(this.optionId)) {
                    source.splice(i, 1);
                    i = 0;
                }
                for (var j = 0; j < remove.length; j++) {
                    console.log('j = ' + j);
                    if (source[i].id === remove[j].id) {
                        console.log('source[' + i + '].id ' + source[i].id + ' remove[' + j + '].id' + remove[j].id);
                        source.splice(i, 1);
                        console.log('break');
                        i = -1;
                        break;
                    }
                }
            }
        },
        async doCheck(index, type) {
            if (type.indexOf('req') === -1) {
                await this.changeOptionsArrays(this.myIncOptions, this.allIncOptions, index, type);
            } else {
                await this.changeOptionsArrays(this.myReqOptions, this.allReqOptions, index, type);
            }
        },
        async changeOptionsArrays(my, all, index, type) {
            if (type.startsWith('add')) {
                const newOption = all.splice(index, 1);
                my.push(...newOption);
                await this.findRequiredOptions(newOption[0].id, my, all);
                if (type.indexOf('req') !== -1) {
                    await this.findIncompatibleOptions(newOption[0].id, all, my);
                }
            } else {
                const remOption = my.splice(index, 1);
                await this.findRequiredOptions(remOption[0].id,  all, my);
                all.push(...remOption);
            }
        },
        async findRequiredOptions(id, dest, source) {
            var requiredOptions = [];
            await axios
                .get('http://localhost:8080/requiredOptions/' + id)
                .then(response => (requiredOptions = response.data));

            if (requiredOptions.length > 0 && source.length > 0) {
                for (var j = 0; j < requiredOptions.length; j++) {
                    for (var i = 0; i < source.length; i++) {
                        if (source[i].id === requiredOptions[j].id) {
                            const reqOpt = source.splice(i, 1);
                            dest.push(...reqOpt);
                        }
                    }
                }
            }
        },
        async findIncompatibleOptions(id, dest, source) {
            await axios
                .get('http://localhost:8080/incompatibleOptions/' + id)
                .then(response => (this.incompatibleOptions = response.data));
            if (this.incompatibleOptions.length > 0 && source.length > 0) {
                for (var j = 0; j < this.incompatibleOptions.length; j++) {
                    for (var i = 0; i < source.length; i++) {
                        if (source[i].id === this.incompatibleOptions[j].id) {
                            //this.hideNotification();
                            const incOpt = source.splice(i, 1);
                            i = 0;
                            //this.removeOptionPrice(incOpt[0]);
                            dest.push(...incOpt);
                        }
                    }
                }
            }
        }
    }
})