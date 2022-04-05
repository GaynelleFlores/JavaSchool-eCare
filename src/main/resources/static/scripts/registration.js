new Vue({
    el: '#app',
    data: {
        login: '',
        password: '',
        phoneNumber: '',
        client: '',
        messages: []
    },
    watch: {
        search: function () {
            this.searching();
        }
    },
    async created() {

    },
    methods: {
        registration: async function () {
            let param = new URLSearchParams();
            param.append('phone', this.phoneNumber);

            await axios({
                method: 'post',
                url: 'http://localhost:8080/clientByPhoneNumber',
                data: param
            }).then(response => (this.client = response.data)).catch(err => this.addMessage(err));

            if (this.client === '') {
                return ;
            }
            let updateParam = new URLSearchParams();
            updateParam.append('login', this.login);
            updateParam.append('password', this.password);
            axios({
                method: 'post',
                url: 'http://localhost:8080/clients/' + this.client.id  + '/createAccount',
                data: updateParam
            }).then(response => (this.addMessage(response.data))).catch(err => this.addMessage(err));
        },
        hideNotification() {
            let vm = this;
            if (this.messages.length) {
                setTimeout(function () {
                    vm.messages.splice(vm.messages.length - 1, 1)
                }, 4000)
            }
        },
        addMessage(message) {
            if (message.response) {
                this.messages.unshift({
                    text: message.response.data,
                    id: Date.now().toLocaleString()});
            } else {
                this.messages.unshift({
                    text: message,
                    id: Date.now().toLocaleString()});
            }
            this.hideNotification();
        }
    }
})