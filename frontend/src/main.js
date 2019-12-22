import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
import VueRouter from 'vue-router'


import { library } from '@fortawesome/fontawesome-svg-core'
import { faDoorOpen, faCog, faFileAlt, faPlusSquare, faEdit, faTrashAlt } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import LoadablePage from './components/LoadablePage.vue'

library.add(faDoorOpen, faCog, faFileAlt, faPlusSquare, faEdit, faTrashAlt)


Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('loadable-page', LoadablePage);

Vue.use(BootstrapVue)
Vue.config.productionTip = false

Vue.use(VueRouter);

new Vue({
  render: h => h(App),
}).$mount('#app')
