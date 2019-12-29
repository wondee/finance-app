<template>
  <v-text-field
    :value="displayValue"
    :rules="valueRules"
    @blur="inputChanged"
    @focus="focus = true"
    required
    :label="label"
  />
</template>
<script>
import { toCurrency } from "../Utils";

export default {
  props: ["id", "label", "value"],
  data() {
    return {
      focus: false,

      valueRules: [
        v => parseInt(v) > 0 || 'Betrag muss positiv und ungleich 0 sein',
      ],
    }
  },
  
  computed: {
    displayValue() {
      return this.focus ? this.value : toCurrency(this.value);
    }
  },
  methods: {
    inputChanged(e) {
      const newValue = Number(e.target.value);
      
      this.$emit("input", isNaN(newValue) ? 0 : newValue);
      this.focus = false;
    }
  }
};
</script>