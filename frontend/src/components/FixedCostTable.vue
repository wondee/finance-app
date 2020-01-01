<template>
  <div>
    <v-simple-table fixed-header v-if="!empty">
      <template v-slot:default>
        <thead>
          <tr>
            <th :key="col.name" v-for="col in filter(cols)" :class="col.styleClass">{{ col.label }}</th>
            <slot name="header" />
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr :key="index" v-for="(entry, index) in entries">
            <td
              :key="col.name"
              v-for="col in filter(cols)"
              :class="col.styleClass"
            >{{ transform(col.transformer, entry[col.name]) }}</td>
            <slot name="content" :entry="entry" />
            <td align="right" class="action-cell">
              <v-btn icon @click="$emit('edit-clicked', entry)">
                <v-icon small>fa-edit</v-icon>
              </v-btn>
              <v-btn icon @click="$emit('delete-clicked', entry)">
                <v-icon small>fa-trash-alt</v-icon>
              </v-btn>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <p v-if="empty">Keine Einträge bisher</p>
  </div>
</template>

<script>
export default {
  props: ["entries", "cols"],
  methods: {
    transform: (f, v) => (f ? f(v) : v),
    filter(cols) {
      return cols.filter(col => !col.hide || this.$vuetify.breakpoint.mdAndUp)
    }
  },
  computed: {
    empty() {
      return !this.entries || this.entries.length == 0;
    },
  }, 
};
</script>