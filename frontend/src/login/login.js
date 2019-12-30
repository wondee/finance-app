import Vue from 'vue'
import Login from './Login.vue'
import vuetify from '../plugins/vuetify';
import VueRouter from 'vue-router'


Vue.use(VueRouter);
Vue.config.productionTip = false

new Vue({
  vuetify,
  render: h => h(Login)
}).$mount('#app')
