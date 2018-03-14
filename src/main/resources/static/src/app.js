import SingleFileComponent from './components/SingleFileComponent.js';

new Vue({
    el: '#app',
    data: {testdata: []},
    mounted() {
        this.fetchTest();
    },
    components: {
        SingleFileComponent
    },
    methods: {
        fetchTest() {
            axios.get("/testrequest").then(function(response) {
                this.testdata = response.data;
            }.bind(this));
        }
    }
});
