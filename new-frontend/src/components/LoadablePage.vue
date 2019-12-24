<template>
    <v-container>

      <v-row v-if="!loaded">
        <v-col>
          <slot name="loading"/>
        </v-col>
      </v-row>

      <slot v-if="loaded" />
    </v-container>
</template>

<script>
export default {
  props: ['endpoint'],
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