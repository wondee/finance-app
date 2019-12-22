import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'


import { library } from '@fortawesome/fontawesome-svg-core'
import { faDoorOpen, faCog, faFileAlt, faPlusSquare } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'


library.add(faDoorOpen)
library.add(faCog)
library.add(faFileAlt)
library.add(faPlusSquare)


Vue.component('font-awesome-icon', FontAwesomeIcon)


Vue.use(BootstrapVue)
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
