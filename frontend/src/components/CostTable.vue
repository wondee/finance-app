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
              <slot name="edit-button" :entry="entry" />
              <delete-button :name="entry.name" @confirm="$emit('delelete-clicked', entry)"/>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <p v-if="empty">Keine Einträge bisher</p>
  </div>
</template>

<script>
import DeleteButton from './DeleteButton';

export default {
  components: {
    DeleteButton
  },
  props: ["entries", "cols"],
  data() {
    return {
      showDelete: false
    }
  },
  methods: {
    transform: (f, v) => (f ? f(v) : v),
    filter(cols) {
      return cols.filter(col => !col.hide || this.$vuetify.breakpoint.mdAndUp);
    }
  },
  computed: {
    empty() {
      return !this.entries || this.entries.length == 0;
    }
  }
};
</script>