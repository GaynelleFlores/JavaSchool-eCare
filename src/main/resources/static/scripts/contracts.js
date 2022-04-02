new Vue({
    el: '#app',
    data: {
        text: '',
        test: false,
        search:'',
        result: [],
        contracts: []
    },
    watch: {
        search: function () {
            this.searching();
        }
    },
    async created() {
        axios.get('http://localhost:8080/allContracts').then(response => (this.contracts = response.data));
        },
    methods: {
        async redirectToDelete(id) {
            if (confirm("Do you really want to delete this contract?")) {
                await axios.delete('http://localhost:8080/contracts/' + id).then(response => console.log(response.data));
            }
        },
        redirectToEdit(id) {
            window.location = 'http://localhost:8080/contracts/' + id + '/editByManager';
        },
        redirectToCreate() {
            window.location = 'http://localhost:8080/createContract';
        },
        searching: function () {
            this.result = [];
            this.test = true;
            if (this.search.length > 0) {
                for (var i = 0; i < this.contracts.length; i++) {
                    if (this.contracts[i].phoneNumber.startsWith(this.search)) {
                        this.result.push(this.contracts[i]);
                    }
                }
            }
        }
    }
})