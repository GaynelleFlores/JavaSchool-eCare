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
            firstOptions: function () {
                console.log("watch");
              this.cartIsActive();
            },
            optionsPrice: function () {
                localStorage.setItem("optionsPrice" + this.contractId, JSON.stringify(this.optionsPrice));
                var total = this.planPrice + this.optionsPrice;
                localStorage.setItem("total" + this.contractId, JSON.stringify(total));
            },
            planPrice: function () {
                localStorage.setItem("planPrice" + this.contractId, JSON.stringify(this.planPrice));
                var total = this.planPrice + this.optionsPrice;
                localStorage.setItem("total" + this.contractId, JSON.stringify(total));
            },
            plan: async function () {
                if (Object.keys(this.firstPlan).length !== 0) {
                    if (JSON.stringify(this.plan) !== JSON.stringify(this.firstPlan)) {
                        localStorage.setItem("plan" + this.contractId, JSON.stringify(this.plan));
                        this.planPrice = this.plan.price;
                    } else {
                        localStorage.removeItem("plan" + this.contractId);
                        this.planPrice = 0;
                    }
                }
                await this.updateOptions();
            },
            chosenOptions: function () {


                localStorage.setItem("chosenOptions" + this.contractId, JSON.stringify(this.chosenOptions));
            }
        },
        mounted() {
            if (localStorage.getItem('chosenOptions' + this.contractId)) {
                this.chosenOptions = JSON.parse(localStorage.getItem('chosenOptions'  + this.contractId));
                console.log(this.chosenOptions);
            }
        },
        async created() {
            var url = window.location.href.split('/');
            this.contractId = url[4];
            axios
                .get('http://localhost:8080/contracts/' + this.contractId + '/get')
                .then(response => (this.contract = response.data));
            if (localStorage.getItem('plan' + this.contractId)) {
                this.plan = JSON.parse(localStorage.getItem('plan'  + this.contractId));
            }
            else {
                await axios
                    .get('http://localhost:8080/getPlan/' + this.contractId)
                    .then(response => (this.plan = response.data));
            }
            await axios
                .get('http://localhost:8080/getPlan/' + this.contractId)
                .then(response => (this.firstPlan = response.data));
            axios
                .get('http://localhost:8080/allPlans')
                .then(response => (this.plans = response.data));
            if (localStorage.getItem('chosenOptions' + this.contractId)) {
                this.chosenOptions = JSON.parse(localStorage.getItem('chosenOptions'  + this.contractId));
                await axios
                    .get('http://localhost:8080/optionsByContract/' + this.contractId)
                    .then(response => (this.firstOptions = response.data));
            }
            else {
                await axios
                    .get('http://localhost:8080/optionsByContract/' + this.contractId)
                    .then(response => (this.chosenOptions = response.data));
                Object.assign(this.firstOptions, this.chosenOptions);
            }
            if (localStorage.getItem('optionsPrice' + this.contractId)) {
                this.optionsPrice = JSON.parse(localStorage.getItem('optionsPrice'  + this.contractId));
            }
            if (localStorage.getItem('planPrice' + this.contractId)) {
                this.planPrice = JSON.parse(localStorage.getItem('planPrice'  + this.contractId));
            }
            await this.updateOptions();
            // await axios
            //     .get('http://localhost:8080/allOptions')
            //     .then(response => (this.options = response.data));
            //console.log(this.firstPlan);
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
            removeOptionPrice(option) {
                if (!this.isOldOption(option.id)) {
                    console.log('old opt price ' + this.optionsPrice);
                    console.log('minus ' + option.connectionCost);
                    this.optionsPrice -= option.connectionCost;
                }
            },
            addOptionPrice(option) {
                if (!this.isOldOption(option.id)) {
                    console.log('old opt price ' + this.optionsPrice);
                    console.log('addOptionPrice plus ' + option.connectionCost);
                    this.optionsPrice += option.connectionCost;
                }
            },
            cartIsActive() {
                for (var i = 0; i < this.chosenOptions.length; i++) {
                    if (this.isOldOption(this.chosenOptions[i]) === false) {
                        return true;
                    }
                }
                return false;
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
                console.log("In refresh " + response);
                this.messages.unshift({
                        text: response,
                        id: Date.now().toLocaleString()});
                this.hideNotification();
                if (response === "Contract updated") {
                    this.optionsPrice = 0;
                    this.planPrice = 0;
                    Object.assign(this.firstOptions, this.chosenOptions);
                    this.firstPlan = this.plan;
                    localStorage.removeItem('chosenOptions' + this.contractId);
                    localStorage.removeItem('optionsPrice' + this.contractId);
                    localStorage.removeItem('plan' + this.contractId);
                    localStorage.removeItem('planPrice' + this.contractId);
                    localStorage.removeItem('total' + this.contractId);
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
                this.optionsPrice = 0;
                if (this.chosenOptions.length !== 0) {
                    for(var i = 0; i < this.chosenOptions.length; i++) {
                        this.addOptionPrice(this.chosenOptions[i]);
                    }
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
                                if (dest == this.chosenOptions) {
                                    this.addOptionPrice(reqOpt[0]);
                                } else {
                                    this.removeOptionPrice(reqOpt[0]);
                                }
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
