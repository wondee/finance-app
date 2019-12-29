<template>
  <v-container>
    <v-row>
      <v-col>
        <v-skeleton-loader
          type="table-tbody"
          :loading="!loaded"
          transition="scale-transition"
          class="mx-auto"
        >
          <v-card>
            <v-card-text>
              <fixed-costs-table
                :entries="entries"
                :cols="cols"
                @edit-clicked="openEdit('special', $event)"
              />
            </v-card-text>
            <v-card-actions>
              <v-btn small text @click="openEdit('special')">Neue Kosten Hinzufügen</v-btn>
            </v-card-actions>
          </v-card>
        </v-skeleton-loader>
      </v-col>
    </v-row>
    <cost-edit-form ref="editForm" />
  </v-container>
</template>

<script>
import LoadablePage from "./LoadablePage";
export default {
  mixins: [LoadablePage],
  data() {
    return {
      entries: []
    }
  },
  created: async function() {
    const data = await this.fetchData("/api/specialcosts");
    this.entries = data;
  },
  
  methods: {
    openEdit(type, cost) {
      this.$refs["editForm"].openEdit(type, cost);
    }
  }
};
</script>