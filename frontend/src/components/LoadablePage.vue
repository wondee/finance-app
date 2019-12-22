<template>
    <b-container>
      <b-row>
        <h2>{{ title }}</h2>
      </b-row>
      
      <b-row v-if="!loaded">
        <img :src="require('../assets/load_indicator.gif')"/>
      </b-row>

      <slot v-if="loaded" />
    </b-container>
</template>

<script>
export default {
  props: ['title', 'endpoint'],
  data() {
    return {
      loaded: false
    }
  },
  created: async function() {
    const response = await fetch(this.endpoint);
    const result = await response.json();
    this.$emit('loaded', result);
    this.loaded = true;
  }
}
</script>

<style scoped>
h2 {
  margin-top: 30px;
  margin-bottom: 30px;
}

</style>