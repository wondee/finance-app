<template>
  <div id="app">
    <Navbar />
    <router-view />
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue';

import VueRouter from 'vue-router';

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

const SpecialCosts = () => import('./components/SpecialCosts.vue');
const Overview = () => import('./components/Overview.vue');
const FixedCosts = () => import('./components/FixedCosts.vue');


const routes = [
  { path: '/', component: Overview },
  { path: '/fixedcosts', component: FixedCosts },
  { path: '/specialcosts', component: SpecialCosts },
]

const router = new VueRouter({
  routes
})

export default {
  name: 'app',
  router, 
  components: {
    Navbar,
  }
}
</script>

<style>
.card {
	width: 100%;
	margin-bottom: 10px;
}

a.icon-link:hover {
  text-decoration: none;
  color: black;
}
a.icon-link {
  color: #6c757d;
}

</style>
