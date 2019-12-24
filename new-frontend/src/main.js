import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import VueRouter from 'vue-router'

import LoadablePage from './components/LoadablePage.vue'

Vue.use(VueRouter);
Vue.config.productionTip = false

Vue.component('loadable-page', LoadablePage);

new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')
