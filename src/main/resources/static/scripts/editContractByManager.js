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
            plan: async function () {
                await this.updateOptions();
            },
        },
        mounted() {

        },
        async created() {
            var url = window.location.href.split('/');
            this.contractId = url[4];
            axios
                .get('http://localhost:8080/contracts/' + this.contractId + '/get')
                .then(response => (this.contract = response.data));

            await axios
                    .get('http://localhost:8080/getPlan/' + this.contractId)
                    .then(response => (this.plan = response.data));

            await axios
                .get('http://localhost:8080/getPlan/' + this.contractId)
                .then(response => (this.firstPlan = response.data));
            axios
                .get('http://localhost:8080/allPlans')
                .then(response => (this.plans = response.data));

            await axios
                .get('http://localhost:8080/optionsByContract/' + this.contractId)
                .then(response => (this.chosenOptions = response.data));
            Object.assign(this.firstOptions, this.chosenOptions);

            await this.updateOptions();

        },
        methods: {
            blockByManager: function (type) {
                let param = new URLSearchParams();
                if (type === 'block') {
                    param.append('isBlocked', 'true');
                    param.append('isBlockedByManager', 'true');
                } else {
                    param.append('isBlocked', 'false');
                    param.append('isBlockedByManager', 'false');
                }
                axios({
                    method: 'post',
                    url: 'http://localhost:8080/contracts/' + this.contractId + '/block',
                    data: param
                }).then(location.reload());
            },
            async updateOptions() {
                await axios
                    .get('http://localhost:8080/getOptionsByPlan/' + this.plan.id)
                    .then(response => (this.options = response.data));
                if (this.options.length === 0) {
                    this.chosenOptions = [];
                    return;
                }
                this.removeNotAllowedOptions();
                for (var i = 0; i < this.options.length; i++) {
                    for (var j = 0; j < this.chosenOptions.length; j++) {
                        if (this.options[i].id === this.chosenOptions[j].id) {
                            this.options.splice(i, 1);
                            i = 0;
                        }
                    }
                }
            },

            removeNotAllowedOptions() {
                var flag = false;
                for (var i = 0; i < this.chosenOptions.length; i++) {
                    for (var j = 0; j < this.options.length; j++) {
                        if (this.options[j].id === this.chosenOptions[i].id) {
                            flag = true;
                        }
                    }
                    if (flag === false) {
                        this.chosenOptions.splice(i, 1);
                        i = 0;
                    }
                    flag = false;
                }
            },
            isOldOption(id) {
                for(var i = 0; i < this.firstOptions.length; i++) {
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
                }).then(response => (this.refreshData(response.data))).catch(err => {
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
            refreshData(response) {
                this.messages.unshift({
                    text: response,
                    id: Date.now().toLocaleString()});
                this.hideNotification();
                if (response === "Contract updated") {
                    Object.assign(this.firstOptions, this.chosenOptions);
                    this.firstPlan = this.plan;
                }
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
                    this.chosenOptions.push(...newOption);

                    await this.findRequiredOptions(newOption[0].id, newOption[0].title, this.chosenOptions, this.options);
                    await this.findIncompatibleOptions(newOption[0].id, newOption[0].title);
                } else {
                    const remOption = this.chosenOptions.splice(index, 1);
                    await this.findRequiredOptions(remOption[0].id, remOption[0].title, this.options, this.chosenOptions);
                    this.options.push(...remOption);
                }
            },
            removeMask(index, type) {
                const toDoList = type === 'add' ? this.options : this.chosenOptions;
                toDoList.splice(index, 1);
            },
            async findRequiredOptions(id, title, dest, source) {
                await axios
                    .get('http://localhost:8080/requiredOptions/' + id)
                    .then(response => (this.requiredOptions = response.data));
                console.log("req " + JSON.stringify(this.requiredOptions));
                console.log("dest " + JSON.stringify(dest));
                console.log("source " + JSON.stringify(dest));
                if (this.requiredOptions.length > 0 && source.length > 0) {
                    for (var j = 0; j < this.requiredOptions.length; j++) {
                        for (var i = 0; i < source.length; i++) {
                            if (source[i].id === this.requiredOptions[j].id) {
                                var message = "You can't pick option \"" + title + "\" without option \"" + source[i].title + "\"";
                                this.messages.unshift({
                                        text: message,
                                        id: Date.now().toLocaleString()
                                    }
                                );
                                this.hideNotification();
                                const reqOpt = source.splice(i, 1);
                                i = 0;
                                dest.push(...reqOpt);
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
                                i = 0;
                                this.options.push(...incOpt);
                            }
                        }
                    }
                }
            }
        }
    }
).mount('#app');
