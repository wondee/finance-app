<template>
  <div id="app">
    <Navbar />
    <Overview/>
  </div>
</template>

<script>
import Overview from './components/Overview.vue';
import Navbar from './components/Navbar.vue';

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

export default {
  name: 'app',
  components: {
    Overview,
    Navbar,
  }
}
</script>

<style>

h1 {
  margin-top: 30px;
  margin-bottom: 30px;
}

a.icon-link:hover {
  text-decoration: none;
  color: black;
}
a.icon-link {
  color: #6c757d;
}

</style>
