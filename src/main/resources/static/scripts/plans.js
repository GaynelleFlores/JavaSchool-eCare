new Vue({
    el: '#app',
    data: {
        text: '',
        test: false,
        search:'',
        result: [],
        contracts: [],
        plans: []
    },
    watch: {
        search: function () {
            this.searching();
        }
    },
    async created() {
        axios.get('http://localhost:8080/allPlans').then(response => (this.plans = response.data));
    },
    methods: {
        redirectToEdit(id) {
            window.location = 'http://localhost:8080/plans/' + id +  '/edit';
        },
        redirectToCreate() {
            window.location = 'http://localhost:8080/createPlan';
        },
        async deleteOption(id) {
            if (confirm("Do you really want to delete this plan? ")) {
                await axios.delete('http://localhost:8080/plans/' + id);
            }
        }
    }
})