<template>
  <v-dialog v-model="dialog" max-width="800" persistent>
    <template v-slot:activator="{ on }">
      <v-btn :icon="!btnText" :text="!!btnText" small v-on="on">
        <v-icon v-if="!btnText" small>{{ btnIcon }}</v-icon>
        <span v-if="!!btnText">{{ btnText }}</span>
      </v-btn>
      <v-snackbar v-model="snackbar" bottom color="success" :timeout="7000">{{ successMsg(name) }}</v-snackbar>
    </template>
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
        <v-btn text @click="dialog = false" :disbaled="saving">Abbrechen</v-btn>
        <v-btn
          text
          @click="$emit('save'); saving = true"
          :disabled="!valid || !changed"
          :loading="saving"
        >Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
export default {
  props: ["title", "changed", "btnText", "icon", "successMsg", "name"],
  data() {
    return {
      valid: false,
      dialog: false,
      saving: false,
      snackbar: false
    };
  },
  computed: {
    btnIcon() {
      return this.icon || "fa-edit";
    }
  },
  methods: {
    success() {
      this.dialog = false;
      this.saving = false;
      this.snackbar = true;
    }
  }
};
</script>