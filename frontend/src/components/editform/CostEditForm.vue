<template>
  <v-dialog v-model="dialog" max-width="800" persistent>
    <v-card v-if="dialog">
      <v-card-title>
        <span>{{ title }}</span>
      </v-card-title>
      <v-card-text>
        <v-form v-model="valid">
          <v-container>
            <slot />
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
export default {
  props: ["title", "changed"],
  data() {
    return {
      valid: false,
      dialog: false,
    };
  },
  methods: {
    openEdit() {
      this.dialog = true;
    },
    onSubmit() {},
    onReset() {}
  }
};
</script>