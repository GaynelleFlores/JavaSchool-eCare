new Vue({
    el: '#app',
    data: {
        text: '',
        clientId: '',
        disabled: true,
        allContracts: []
    },
    async created() {
        var url = window.location.href.split('/');
        this.clientId = url[4];
        axios.get('http://localhost:8080/clients/' + this.clientId + '/getContracts').then(response => (this.allContracts = response.data));

    },
    methods: {
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