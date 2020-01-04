<template>
  <v-dialog v-model="show" max-width="600">
    <template v-slot:activator="{ on }">
      <v-btn icon v-on="on">
        <v-icon small>fa-trash-alt</v-icon>
      </v-btn>
    </template>
    <v-card>
      <v-card-title>
        <span>'{{ name }}' Löschen</span>
      </v-card-title>
      <v-card-text>
        <p>
          Der Posten
          <strong>{{ name }}</strong> wird unwiderruflich gelöscht.
        </p>

        <p>Sind Sie sich sicher?</p>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn @click="show = false" text>Abbrechen</v-btn>
        <v-btn @click="show = false; $emit('confirm')" color="error">Löschen</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
export default {
  props: ["name"],
  data() {
    return {
      show: false
    };
  }
};
</script>