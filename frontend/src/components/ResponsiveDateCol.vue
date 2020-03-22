<template>
  <td  align="right">
    <v-dialog v-model="dialog" width="500" v-if="visible">
      <template v-slot:activator="{ on }">
        <v-icon small v-on="on">fa-calendar-alt</v-icon>
      </template>
      <v-card>
        <v-card-title>{{ entry.name }}</v-card-title>

        <v-card-text>
          <div v-if="entry.from">
            Ab
            <strong>{{ entry.from | displayLongMonth }}</strong>
          </div>
          <div v-if="entry.to">
            Bis
            <strong>{{ entry.to | displayLongMonth }}</strong>
          </div>
        </v-card-text>
      </v-card>
    </v-dialog>
  </td>
</template>
<script>

export default {
  props: ["entry"],
  computed: {
    visible() {
      return this.entry.from || this.entry.to;
    }
  },
  data() {
    return {
      dialog: false
    };
  }
};
</script>