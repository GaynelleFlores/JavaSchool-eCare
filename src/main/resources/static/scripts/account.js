new Vue({
    el: '#app',
    data: {
        text: '',
        clientId: '',
        disabled: true,
        allContracts: [],
        cartPlans: [],
        cartOptions: []
    },
    async created() {
        var url = window.location.href.split('/');
        this.clientId = url[4];
        await axios.get('http://localhost:8080/clients/' + this.clientId + '/getContracts').then(response => (this.allContracts = response.data));
        this.createCart();
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
                return JSON.parse(localStorage.getItem('chosenOptions' + id));
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
            console.log(JSON.stringify(this.cartOptions));
            console.log(JSON.stringify(this.cartPlans));
        },
        deleteContract: function (contractId) {
            if(confirm("Do you really want to delete this contract? ")){
                axios.delete('http://localhost:8080/contracts/' + contractId);
                location.reload();
            }
        },
        block: function (contractId) {
            console.log('in Block ' + contractId);
            let param = new URLSearchParams();
            param.append('isBlocked', 'true');
            param.append('isBlockedByManager', 'false');
            axios({
                method: 'post',
                url: 'http://localhost:8080/contracts/' + contractId + '/block',
                data: param
            });
            location.reload();
        },
        unblock: function (contractId) {
            console.log('in UnBlock ' + contractId);
            let param = new URLSearchParams();
            param.append('isBlocked', 'false');
            param.append('isBlockedByManager', 'false');
            axios({
                method: 'post',
                url: 'http://localhost:8080/contracts/' + contractId + '/block',
                data: param
            });
            location.reload();
        },
        isDisabled: function(isBlock) {
            console.log("In script!")
            return isBlock;
        }
    }
})