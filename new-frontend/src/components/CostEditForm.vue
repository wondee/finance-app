<template>
  <v-dialog v-model="dialog" max-width="800">
    <v-card v-if="dialog">
      <v-toolbar>
        <v-btn icon @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-toolbar-title>{{ title }}</v-toolbar-title>
      </v-toolbar>
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
                <v-checkbox v-model="form.incoming" label="Eingehend" color="primary"></v-checkbox>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="dialog = false">Abbrechen</v-btn>
        <v-btn text @click="dialog = false">Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import CurrencyInput from "./CurrencyInput";

const Type = {
  monthly: {
    title: 'Monatliche Kosten' 
  }, 
  quaterly: {
    title: 'Vierteljährliche Kosten'
  },
  halfyearly: {
    title: 'Halbjährige Kosten' 
  },
  yearly: {
    title: 'Jährliche Kosten' 
  },
}

export default {
  components: {
    CurrencyInput
  },
  data() {
    return {
      valid: false,
      dialog: false,
      form: {
        name: "",
        amount: 0,
        incoming: false
      },

      nameRules: [
        v => !!v || "Bezeichnung darf nicht leer sein",
        v => v.length <= 20 || "Bezeichnung muss weniger als 20 Zeichen haben"
      ]
    };
  },
  computed: {
    title() {
      return `${this.type.title} ${this.cost ? "ändern" : "hinzufügen"}`
    }
  },
  methods: {
    openEdit(type, cost) {

      this.type = Type[type];
      this.cost = cost;

      if (cost) {
        this.form.name = cost.name;
        this.form.amount = Math.abs(cost.amount);
        this.form.incoming = cost.amount > 0;
      }

      this.dialog = true;
    },
    onSubmit() {},
    onReset() {}
  }
};
</script>