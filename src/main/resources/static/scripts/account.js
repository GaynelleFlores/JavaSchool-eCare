new Vue({
    el: '#app',
    data: {
        text: '',
        clientId: '',
        disabled: true,
        allContracts: [],
        cartPlans: [],
        cartOptions: [],
        allOptions: []
    },
    async created() {
        var url = window.location.href.split('/');
        this.clientId = url[4];
        await axios.get('http://localhost:8080/clients/' + this.clientId + '/getContracts').then(response => (this.allContracts = response.data)).catch();
        this.createCart();
        for (var i = 0; i < this.allContracts.length; i++) {
            var temp = [];
            await axios
                .get('http://localhost:8080/optionsByContract/' + this.allContracts[i].id)
                .then(response => (temp = response.data));
            this.allOptions.push({id: this.allContracts[i].id, options: temp});
        }
    },
    methods: {
        totalForContract: function (id) {
            var total;
            if (localStorage.getItem('total' + id)) {
                total = JSON.parse(localStorage.getItem('total' + id));
                return total;
            }
            return 0;
        },
        planForContract: function (id) {
            if (localStorage.getItem('plan' + id)) {
                return JSON.parse(localStorage.getItem('plan' + id));
            }
            return '';
        },
        optionsForContract: function (id) {
            if (localStorage.getItem('chosenOptions' + id)) {
                var temp = JSON.parse(localStorage.getItem('chosenOptions' + id));
                if (JSON.stringify(temp) === "[]") {
                    return '';
                }
                return temp;
            }
            return '';
        },
        createCart: function () {
            for(var i = 0; i < this.allContracts.length; i++) {
                if (localStorage.getItem('plan' + this.allContracts[i].id)) {
                    this.cartPlans.push(JSON.parse(localStorage.getItem('plan' + this.allContracts[i].id)));
                }
                if (localStorage.getItem('chosenOptions' + this.allContracts[i].id)) {
                    this.cartOptions.push(JSON.parse(localStorage.getItem('chosenOptions' + this.allContracts[i].id)));
                }
            }
        },
        deleteContract: function (contractId) {
            if(confirm("Do you really want to delete this contract? ")){
                axios.delete('http://localhost:8080/contracts/' + contractId);
                location.reload();
            }
        },
        block: function (contractId) {
            let param = new URLSearchParams();
            param.append('isBlocked', 'true');
            param.append('isBlockedByManager', 'false');
            axios({
                method: 'post',
                url: 'http://localhost:8080/contracts/' + contractId + '/block',
                data: param
            });
        },
        contractOptions: function (id) {
            for (var i = 0; i < this.allOptions.length; i++) {
                if (this.allOptions[i].id === id) {
                    return this.allOptions[i].options;
                }
            }
            return [];
        },
        unblock: function (contractId) {
            let param = new URLSearchParams();
            param.append('isBlocked', 'false');
            param.append('isBlockedByManager', 'false');
            axios({
                method: 'post',
                url: 'http://localhost:8080/contracts/' + contractId + '/block',
                data: param
            });
        },
        isDisabled: function(isBlock) {
            return isBlock;
        }
    }
})