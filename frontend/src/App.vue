<template>
<layout>
  <router-view v-if="user"/>
  <v-container>
    <v-row>
      <v-col>
        <h1>Loading User...</h1> 
      </v-col>
    </v-row>
  </v-container>
</layout>
</template>

<script>
import VueRouter from "vue-router";
import Layout from "./Layout";

const Overview = () => import("./components/overview/Overview.vue");
const FixedCosts = () => import("./components/FixedCosts.vue");
const SpecialCosts = () => import("./components/SpecialCosts.vue");

const routes = [
  { path: "/", component: Overview },
  { path: "/fixedcosts", component: FixedCosts },
  { path: "/specialcosts", component: SpecialCosts }
];

const router = new VueRouter({
  routes
});

export default {
  router,
  components: {
    Layout
  },
  data() {
    return {
      user: null
    }
  },
  created: async function() {
    const data = await fetch("/api/user");
    console.log(data)

  }
};
</script>
