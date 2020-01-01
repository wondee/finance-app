<template>
  <v-menu
    v-model="menu"
    :close-on-content-click="false"
    :nudge-right="40"
    transition="scale-transition"
    offset-y
    min-width="290px"
  >
    <template v-slot:activator="{ on }">
      <v-text-field
        :value="displayDate"
        :label="label"
        prepend-icon="fa-calendar-week"
        :error-messages="errorMessages"
        clearable
        clear-icon="mdi-close"
        readonly
        v-on="on"
      />
    </template>
    <v-date-picker
      :value="value"
      @input="input"
      type="month"
      color="blue"
      :min="minDate"
      :max="max"
    />
  </v-menu>
</template>
<script>
import { displayLongMonth } from "../Utils";


const nowDate = new Date();

export default {
  props: ["value", "min", "max", "label", "rules"],

  data() {
    return {
      menu: false,
      now: `${nowDate.getFullYear()}-${nowDate.getMonth() + 1}`,
      errorMessages: []
    };
  },
  computed: {
    displayDate() {
      if (!this.value) return null;
      const elems = this.value.split("-");
      return displayLongMonth(elems);
    },
    minDate() {
      return this.min || this.now;
    }
  },
  methods: {
    input(e) {
      this.$emit("input", e);
      this.menu = false;

      this.errorMessages = [];

      if (!this.rules) return;

      this.rules.forEach(rule => {
        const result = rule(e);
        if (typeof result === "string") {
          this.errorMessages.push(result);
        }
      });
    }
  }
};
</script>