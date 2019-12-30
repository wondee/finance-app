<template>
<layout>
  <router-view />
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
};
</script>

<style scoped>
.v-card.div {
  width: 100%;
}
h2 {
  margin-top: 30px;
  margin-bottom: 30px;
}
</style>