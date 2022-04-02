new Vue({
    el: '#app',
    data: {
        text: '',
        test: false,
        search:'',
        result: [],
        contracts: [],
        clients: []
    },
    watch: {
        search: function () {
            this.searching();
        }
    },
    async created() {
        axios.get('http://localhost:8080/allClients').then(response => (this.clients = response.data));
        axios.get('http://localhost:8080/allContracts').then(response => (this.contracts = response.data));
    },
    methods: {
        async redirectToDelete(id) {
            if (confirm("Do you really want to delete this client?")) {
                await axios.delete('http://localhost:8080/clients/' + id);
            }
        },
        redirectToEdit(id) {
            window.location = 'http://localhost:8080/clients/' + id + '/edit';
        },
        redirectToCreate() {
            window.location = 'http://localhost:8080/createClient';
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