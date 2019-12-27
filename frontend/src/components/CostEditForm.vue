<template>
  <v-dialog v-model="dialog" max-width="800" persistent>
    <v-card v-if="dialog">
      <v-card-title>
        <span>{{ title }}</span>
      </v-card-title>
      <v-card-text>
        <v-form v-model="valid">
          <v-container>
            <v-row>
              <v-col>
                <v-text-field
                  v-model="form.name"
                  :rules="nameRules"
                  :counter="20"
                  label="Bezeichnung"
                  required
                />
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <currency-input label="Betrag" v-model="form.amount" />
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-checkbox v-model="form.incoming" label="Eingehend" color="primary" />
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <month-date-picker v-model="form.from" :max="form.to" />
              </v-col>
              <v-col>
                <month-date-picker v-model="form.to" :rules="toDateRules" :min="form.from" />
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="dialog = false">Abbrechen</v-btn>
        <v-btn text @click="dialog = false" :disabled="!valid || !changed">Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import CurrencyInput from "./CurrencyInput";
import MonthDatePicker from "./MonthDatePicker";
import { equals } from './Utils';

const Type = {
  monthly: {
    title: "Monatliche Kosten"
  },
  quaterly: {
    title: "Vierteljährliche Kosten"
  },
  halfyearly: {
    title: "Halbjährige Kosten"
  },
  yearly: {
    title: "Jährliche Kosten"
  }
};

export default {
  components: {
    CurrencyInput,
    MonthDatePicker
  },
  data() {
    return {
      valid: false,
      dialog: false,

      cost: null,

      form: null,

      nameRules: [
        v => !!v || "Bezeichnung darf nicht leer sein",
        v => v.length <= 20 || "Bezeichnung muss weniger als 20 Zeichen haben"
      ],

      toDateRules: [
        d =>
          !d ||
          !this.form.from ||
          new Date(d) >= new Date(this.form.from) ||
          "'Gültig bis' darf nicht kleiner als 'Gültig ab' sein"
      ]
    };
  },
  computed: {
    title() {
      return `${this.type.title} ${this.cost ? "ändern" : "hinzufügen"}`;
    },
    changed() {
      return !equals(this.form, costToForm(this.cost));
    }
  },
  methods: {
    openEdit(type, cost) {
      this.type = Type[type];
      this.cost = cost;
      this.form = costToForm(this.cost);
      this.dialog = true;
    },
    onSubmit() {},
    onReset() {}
  }
};

const costToForm = cost =>
  cost
    ? {
        name: cost.name,
        amount: Math.abs(cost.amount),
        incoming: cost.amount > 0,
        from: cost.from,
        to: cost.to
      }
    : {
        name: "",
        amount: 0,
        incoming: false,
        from: null,
        to: null
      };
</script>