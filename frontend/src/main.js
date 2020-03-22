import './finance.css'
import Vue from 'vue'
import App from './App'
import vuetify from './plugins/vuetify';
import VueRouter from 'vue-router'

import LoadablePage from './components/LoadablePage'
import { toCurrency, displayMonth } from './components/Utils' 


Vue.use(VueRouter);
Vue.config.productionTip = false

Vue.component('loadable-page', LoadablePage);

Vue.filter('currency', toCurrency);
Vue.filter('displayMonth', ({ yearMonth }) => displayMonth(yearMonth));
Vue.filter('displayLongMonth',  yearMonth  => displayMonth(yearMonth, false));


new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')
