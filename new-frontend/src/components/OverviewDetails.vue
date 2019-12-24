<template>
  <v-dialog v-model="show">
    <v-card v-if="detail">
      <v-card-title class="healine">{{ 'Details vom ' + detail.month }}</v-card-title>
      <v-skeleton-loader v-if="!loaded" type="table-tbody" class="mx-auto"></v-skeleton-loader>
      <v-card-text v-if="loaded">
        <div v-if="specialCosts && specialCosts.length > 0">
          <h4>Sonderkosten:</h4>
          <v-simple-table>
            <template v-slot:default>
              <tbody>
                <tr :key="index" v-for="(cost, index) in specialCosts">
                  <td>{{ cost.name }}</td>
                  <td>{{ cost.displayAmount }}</td>
                  <td>
                    <v-btn
                      icon
                      :to="'/fixedcosts/edit?target=overview&id=' + cost.id + '&type=special'"
                    >
                      <v-icon>fa-edit</v-icon>
                    </v-btn>
                  </td>
                  <td>
                    <v-btn icon :to="'/specialcosts/delete?target=overview&id=' + cost.id">
                      <v-icon>fa-trash-alt</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </div>

        <p></p>
        <div v-if="specialCosts && fixedCosts.length > 0">
          <h4>Fixkosten:</h4>
          <v-simple-table fixed-header>
            <template v-slot:default>
              <tbody>
                <tr :key="index" v-for="(cost, index) in fixedCosts">
                  <td>{{ cost.name }}</td>
                  <td>{{ cost.displayAmount }}</td>
                  <td>{{ cost.displayType }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: ["detail"],
  data() {
    return {
      fixedCosts: null,
      specialCosts: null
    };
  },
  computed: {
    show: {
      get() {
        return !!this.detail;
      }, 
      set(val) {
        if (!val) this.$emit('close');
      }
    },
    loaded() {
      return this.fixedCosts;
    }
  },
  watch: {
    detail(val) {
      this.fixedCosts = null;
      this.specialCosts = null;

      if (val) {
        this.loadData(val.index);
      }
    }
  },
  methods: {
    loadData: async function(index) {
      const response = await fetch("/api/overview/detail?index=" + index);
      const result = await response.json();
      this.fixedCosts = result.fixedCosts || [];
      this.specialCosts = result.specialCosts || [];
    }
  }
};
</script>