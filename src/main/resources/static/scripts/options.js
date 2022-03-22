new Vue({
    el: '#app',
    data: {
        text: '',
        test: false,
        search:'',
        result: [],
        contracts: [],
        options: []
    },
    watch: {
        search: function () {
            this.searching();
        }
    },
    async created() {
        axios.get('http://localhost:8080/allOptions').then(response => (this.options = response.data));
    },
    methods: {
        redirectToEdit(id) {
            window.location = 'http://localhost:8080/options/' + id +  '/edit';
        },
        redirectToCreate() {
            window.location = 'http://localhost:8080/createOption';
        },
        deleteOption(id) {
            if(confirm("Do you really want to delete this option? ")){
                axios.delete('http://localhost:8080/options/' + id);
                window.location.reload();
            }
        }
    }
})