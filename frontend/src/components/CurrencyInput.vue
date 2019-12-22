<template>
  <b-form-input 
    :id="id" 
    :value="displayValue" 
    @blur="inputChanged" 
    @focus="cleanInput" 
    required 
  />
</template>
<script>
import { toCurrency } from "./Utils";

export default {
  props: ['id', 'value'],
  computed: {
    displayValue() {
      return toCurrency(this.value);
    }
  },
  methods: {
    inputChanged(e) {
      var newValue = Number(e.target.value);

      this.value = isNaN(newValue) ? 0 : newValue;
      this.$emit("input", this.value);

      e.target.value = toCurrency(this.value);
    },
    cleanInput(e) {
      e.target.value = this.value;
    }
  }
};
</script>