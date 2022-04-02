new Vue({
    el: '#app',
    data: {
        title: 'Title',
        price: 0,
        connectionCost: 0,
        allOptions: [],
        allowedOptions: [],
        finalPlan: {
            id:0, title:'', price:0, allowedOptions:[]
        }
    },
    watch: {

    },
    async created() {
        var url = window.location.href.split('/');
        this.planId = url[4];

        await axios
            .get('http://localhost:8080/allOptions')
            .then(response => (this.allOptions = response.data));
    },
    methods: {
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
            this.finalPlan.id = this.planId;
            this.finalPlan.title = this.title;
            this.finalPlan.price = this.price;
            param.append('plan', JSON.stringify(this.finalPlan));
            this.addSetToParam(param, this.allowedOptions, 'allowedOptions')
            axios({
                method: 'post',
                url: 'http://localhost:8080/plans/create',
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
        async doCheck(index, type) {
            await this.changeOptionsArrays(this.allowedOptions, this.allOptions, index, type);
        },
        async changeOptionsArrays(my, all, index, type) {
            if (type.startsWith('add')) {
                const newOption = all.splice(index, 1);
                my.push(...newOption);
                await this.findRequiredOptions(newOption[0].id, my, all);
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
        }
    }
})