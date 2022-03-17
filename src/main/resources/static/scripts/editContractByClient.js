var app = new Vue({
        el: '#app',
        data() {
            return {
                show: true,
                valueInput: '',
                contractId: '',
                contract: '',
                firstPlan: '',
                plan: 'empty',
                planPrice: 0,
                optionsPrice: 0,
                plans: [],
                options: [],
                firstOptions: [],
                chosenOptions: [],
                requiredOptions: [],
                incompatibleOptions: [],
                messages: [],
                timeout: {
                    type: Number,
                    default: 1000
                }
            };
        },
        watch: {
            plan: function () {
                if (Object.keys(this.firstPlan).length !== 0) {
                    if (JSON.stringify(this.plan) !== JSON.stringify(this.firstPlan)) {
                        this.planPrice = this.plan.price;
                    } else {
                        this.planPrice = 0;
                    }
                }
            }
        },
        async created() {
            var url = window.location.href.split('/');
            this.contractId = url[4];
            axios
                .get('http://localhost:8080/contracts/' + this.contractId + '/get')
                .then(response => (this.contract = response.data));
            axios
                .get('http://localhost:8080/getPlan/' + this.contractId)
                .then(response => (this.plan = response.data));
            axios
                .get('http://localhost:8080/getPlan/' + this.contractId)
                .then(response => (this.firstPlan = response.data));
            axios
                .get('http://localhost:8080/getPlansList')
                .then(response => (this.plans = response.data));

            await axios
                .get('http://localhost:8080/optionsByContract/' + this.contractId)
                .then(response => (this.chosenOptions = response.data));

            await axios
                .get('http://localhost:8080/allOptions')
                .then(response => (this.options = response.data));
            for (var i = 0; i < this.options.length; i++) {
                for (var j = 0; j < this.chosenOptions.length; j++) {
                    if (this.options[i].id === this.chosenOptions[j].id) {
                        this.options.splice(i, 1);
                    }
                }
            }
            Object.assign(this.firstOptions, this.chosenOptions);
        },
        methods: {
            removeOptionPrice(option) {
                if (!this.isOldOption(option.id)) {
                    this.optionsPrice -= option.connectionCost;
                }
            },
            addOptionPrice(option) {
                if (!this.isOldOption(option.id)) {
                    this.optionsPrice += option.connectionCost;
                }
            },
            isOldOption(id) {
                for(var i = 0; i < this.firstOptions.length; i++) {
                    console.log("optid =" + this.firstOptions[i].id );
                    console.log("id =" + id );
                    if (this.firstOptions[i].id === id) {
                        return true;
                    }
                }
                return false;
            },
            post() {
                let param = new URLSearchParams();
                param.append('plan', JSON.stringify(this.plan));
                if (this.chosenOptions.length === 0) {
                    param.append('options', 'empty');
                } else if (this.chosenOptions.length === 1) {
                    param.append('options', JSON.stringify(this.chosenOptions[0]));
                }
                for (var i = 0; i < this.chosenOptions.length; i++) {
                    param.append('options', JSON.stringify(this.chosenOptions[i]));
                }

                var url = 'http://localhost:8080/contracts/' + this.contractId;
                axios({
                    method: 'post',
                    url: 'http://localhost:8080/contracts/' + this.contractId  + '/update',
                    data: param
                }).then(response => (this.messages.unshift({
                    text: response.data,
                    id: Date.now().toLocaleString()
                }))).then(this.hideNotification).catch(err => {
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
            hideNotification() {
                let vm = this;
                if (this.messages.length) {
                    setTimeout(function () {
                        vm.messages.splice(vm.messages.length - 1, 1)
                    }, 4000)
                }
            },
            async doCheck(index, type) {
                if (type === 'add') {
                    const newOption = this.options.splice(index, 1);
                    this.addOptionPrice(newOption[0]);
                    this.chosenOptions.push(...newOption);
                    await this.findRequiredOptions(newOption[0].id, newOption[0].title);
                    await this.findIncompatibleOptions(newOption[0].id, newOption[0].title);

                } else {
                    const remOption = this.chosenOptions.splice(index, 1);
                    this.removeOptionPrice(remOption[0]);
                    this.options.push(...remOption);
                }
            },
            removeMask(index, type) {
                const toDoList = type === 'add' ? this.options : this.chosenOptions;
                toDoList.splice(index, 1);
            },
            async findRequiredOptions(id, title) {
                await axios
                    .get('http://localhost:8080/requiredOptions/' + id)
                    .then(response => (this.requiredOptions = response.data));
                if (this.requiredOptions.length > 0 && this.options.length > 0) {
                    for (var j = 0; j < this.requiredOptions.length; j++) {
                        for (var i = 0; i < this.options.length; i++) {
                            if (this.options[i].id === this.requiredOptions[j].id) {
                                var message = "You can't pick option \"" + title + "\" without option \"" + this.options[i].title + "\"";
                                this.messages.unshift({
                                        text: message,
                                        id: Date.now().toLocaleString()
                                    }
                                );
                                this.hideNotification();
                                const reqOpt = this.options.splice(i, 1);
                                this.addOptionPrice(reqOpt[0]);
                                this.chosenOptions.push(...reqOpt);
                            }
                        }
                    }
                }
            },
            async findIncompatibleOptions(id, title) {
                await axios
                    .get('http://localhost:8080/incompatibleOptions/' + id)
                    .then(response => (this.incompatibleOptions = response.data));
                if (this.incompatibleOptions.length > 0 && this.chosenOptions.length > 0) {
                    for (var j = 0; j < this.incompatibleOptions.length; j++) {
                        for (var i = 0; i < this.chosenOptions.length; i++) {
                            if (this.chosenOptions[i].id === this.incompatibleOptions[j].id) {
                                var message = "Option \"" + title + "\" is incompatible with option \"" + this.chosenOptions[i].title + "\"";
                                this.messages.unshift({
                                        text: message,
                                        id: Date.now().toLocaleString()
                                    }
                                );
                                this.hideNotification();
                                const incOpt = this.chosenOptions.splice(i, 1);
                                this.removeOptionPrice(incOpt[0]);
                                this.options.push(...incOpt);
                            }
                        }
                    }
                }
            }
        }
    }
).mount('#app');
