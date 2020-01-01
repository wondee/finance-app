<template>
  <v-banner single-line sticky :icon="icon" elevation="4">
    Aktueller Betrag:
    <strong :class="{ red : currentAmount < 0 }">{{ currentAmount | currency }}</strong>
    <template v-slot:actions>
      <v-btn text @click="show = true">Ändern</v-btn>
    </template>
    <v-dialog v-model="show" max-width="600">
      <v-card>
        <v-card-title>
          <span>Aktuellen Betrag ändern</span>
        </v-card-title>
        <v-form v-model="valid">
          <v-container>
            <currency-input label="Betrag" v-model="newAmount" />
          </v-container>
        </v-form>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="show = false">Abbrechen</v-btn>
          <v-btn text @click="show = false">Speichern</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-banner>
</template>
<script>
import CurrencyInput from "../editform/CurrencyInput";

export default {
  components: {
    CurrencyInput
  },
  props: ["currentAmount"],
  computed: {
    icon() {
      return this.$vuetify.breakpoint.smAndUp ? "fa-piggy-bank" : "";
    }
  },
  data() {
    return {
      valid: true,
      show: false,
      newAmount: this.currentAmount
    };
  }
};
</script>