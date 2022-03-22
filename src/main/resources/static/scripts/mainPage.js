new Vue({
    el: '#app',
    data: {

    },
    methods: {
        redirectToContracts() {
            window.location = 'http://localhost:8080/contracts';
        },
        redirectToClients() {
            window.location = 'http://localhost:8080/clients';
        },
        redirectToPlans() {
            window.location = 'http://localhost:8080/plans';
        },
        redirectToOptions() {
            window.location = 'http://localhost:8080/options/';
        }
    }
})