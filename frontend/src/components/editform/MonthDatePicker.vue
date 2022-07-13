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
      :value="date"
      @input="input"
      type="month"
      color="blue"
      :min="minDate"
      :max="max"
    />
  </v-menu>
</template>
<script>
import { displayMonth } from "../Utils";

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
      return displayMonth(this.value, false, null);
    },
    date() {
      return this.value ? this.value.year + '-' + this.value.month : null;
    },
    minDate() {
      return this.min || this.now;
    }
  },
  methods: {
    input(e) {

      const createYearMonth = () => {
        const elems = e.split('-')
        return {year: elems[0], month: elems[1]}
      }

      const inputYearMonth = e ? createYearMonth() : e;
      this.$emit("input", inputYearMonth);
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