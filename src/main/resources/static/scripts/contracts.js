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
        redirectToDelete(id) {
            if(confirm("Do you really want to delete this contract?")){
                axios.delete('http://localhost:8080/contracts/' + id);
                window.location.reload();
            }
        },
        redirectToEdit(id) {
            window.location = 'http://localhost:8080/contracts/' + id + '/editByManager';
        },
        searching: function () {
            this.result = [];
            this.test = true;
            console.log(this.result.length !== 0)
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