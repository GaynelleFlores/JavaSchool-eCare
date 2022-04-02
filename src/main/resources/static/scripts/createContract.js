var app = new Vue({
        el: '#app',
        data() {
            return {
                show: true,
                valueInput: '',
                contractId: '',
                contract: '',
                plan: '',
                plans: [],
                client: '',
                clients: [],
                options: [],
                chosenOptions: [],
                requiredOptions: [],
                incompatibleOptions: [],
                messages: [],
                phoneNumber: '',
                timeout: {
                    type: Number,
                    default: 1000
                }
            };
        },
        watch: {
            plan: async function () {
                await this.updateOptions();
            }
        },
        async created() {
            const CSRF_TOKEN = document.cookie.match(new RegExp(`XSRF-TOKEN=([^;]+)`))[1];
            const instance = axios.create({
                headers: { "X-XSRF-TOKEN": CSRF_TOKEN }
            });
            export const AXIOS = instance;

            axios
                .get('http://localhost:8080/allClients')
                .then(response => (this.clients = response.data));
            axios
                .get('http://localhost:8080/allPlans')
                .then(response => (this.plans = response.data));
            await axios
                .get('http://localhost:8080/allOptions')
                .then(response => (this.options = response.data));
        },
        methods: {
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
                            i = -1;
                            break;
                        }
                    }
                }
            },
            removeNotAllowedOptions() {
                for (var i = 0; i < this.chosenOptions.length; i++) {
                    for (var j = 0; j < this.options.length; j++) {
                        if (this.options[j].id === this.chosenOptions[i].id) {
                            this.chosenOptions.splice(i, 1);
                            i = -1;
                            break;
                        }
                    }
                }
            },
            post() {
                if (this.phoneNumber === '' || this.client === '' || this.plan === '') {
                    this.messages.unshift({
                        text: "Add phone number, chose client and plan",
                        id: Date.now().toLocaleString()});
                    this.hideNotification();
                    return;
                }

                let param = new URLSearchParams();
                param.append('phoneNumber', this.phoneNumber);
                param.append('plan', JSON.stringify(this.plan));
                param.append('client', JSON.stringify(this.client));
                if (this.chosenOptions.length === 0) {
                    param.append('options', 'empty');
                } else if (this.chosenOptions.length === 1) {
                    param.append('options', JSON.stringify(this.chosenOptions[0]));
                }
                for (var i = 0; i < this.chosenOptions.length; i++) {
                    param.append('options', JSON.stringify(this.chosenOptions[i]));
                }

                axios({
                    method: 'post',
                    url: 'http://localhost:8080/contracts/create',
                    data: param
                }).then(response => (this.refreshData(response.data))).catch(err => this.addMessage(err));
            },
            addMessage(message) {
                if (message.response) {
                    this.messages.unshift({
                        text: message.response.data,
                        id: Date.now().toLocaleString()});
                } else {
                    this.messages.unshift({
                        text: message,
                        id: Date.now().toLocaleString()});
                }
                this.hideNotification();
            },
            refreshData(response) {
                this.messages.unshift({
                    text: response,
                    id: Date.now().toLocaleString()});
                this.hideNotification();
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