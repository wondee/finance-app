<template>
  <v-dialog v-model="dialog" max-width="800">
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
        <v-btn text @click="dialog = false" :disabled="!valid">Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import CurrencyInput from "./CurrencyInput";
import MonthDatePicker from './MonthDatePicker';

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

      form: {
        name: "",
        amount: 0,
        incoming: false,
        from: null,
        to: null
      },

      nameRules: [
        v => !!v || "Bezeichnung darf nicht leer sein",
        v => v.length <= 20 || "Bezeichnung muss weniger als 20 Zeichen haben"
      ],

      toDateRules: [d => 
        !d || !this.form.from || new Date(d) >= new Date(this.form.from) || 
        "'Gültig bis' darf nicht kleiner als 'Gültig ab' sein" ]
    };
  },
  computed: {
    title() {
      return `${this.type.title} ${this.cost ? "ändern" : "hinzufügen"}`;
    },
  },
  methods: {
    openEdit(type, cost) {
      this.type = Type[type];
      this.cost = cost;

      if (cost) {
        this.form.name = cost.name;
        this.form.amount = Math.abs(cost.amount);
        this.form.incoming = cost.amount > 0;
        this.form.from = cost.from;
        this.form.to = cost.to;
      }

      this.dialog = true;
    },
    onSubmit() {},
    onReset() {}
  }
};
</script>