<template>
  <v-dialog v-model="show">
    <template v-slot:activator="{ on }">
      <v-btn icon v-on="on">
        <v-icon small>fa-file-alt</v-icon>
      </v-btn>
    </template>
    <v-card v-if="detail">
      <v-card-title class="healine">Details vom {{ detail.yearMonth | displayLongMonth }}</v-card-title>
      <v-skeleton-loader
        :loading="!loaded"
        transition="scale-transition"
        type="table-tbody"
        class="mx-auto"
      >
        <v-card-text>
          <div v-if="specialCosts && specialCosts.length > 0">
            <h3>Sonderkosten:</h3>
            <v-simple-table>
              <template v-slot:default>
                <tbody>
                  <tr :key="index" v-for="(cost, index) in specialCosts">
                    <td>{{ cost.name }}</td>
                    <td>{{ cost.amount | currency }}</td>
                    <td align="right">
                      <special-cost-form :cost="cost" />
                      <delete-button :name="cost.name" @confirm="deleteSpecialCost(cost.id)" />
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>

          <p></p>
          <div v-if="specialCosts && fixedCosts.length > 0">
            <h3>Fixkosten:</h3>
            <v-simple-table fixed-header>
              <template v-slot:default>
                <tbody>
                  <tr :key="index" v-for="(cost, index) in fixedCosts">
                    <td>{{ cost.name }}</td>
                    <td>{{ cost.amount | currency }}</td>
                    <td>{{ cost.displayType }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
        </v-card-text>
      </v-skeleton-loader>
    </v-card>
  </v-dialog>
</template>

<script>
import LoadablePage from "../LoadablePage";
import DeleteButton from "../DeleteButton";
import SpecialCostForm from "../editform/SpecialCostForm";
export default {
  mixins: [LoadablePage],
  components: { DeleteButton, SpecialCostForm },
  props: ["detail"],
  data() {
    return {
      show: false,
      fixedCosts: null,
      specialCosts: null
    };
  },
  watch: {
    show(val) {
      if (val && !this.fixedCosts && !this.specialCosts) {
        this.loadData(this.detail.index);
      }
    }
  },
  methods: {
    loadData: async function(index) {
      const result = await this.fetchData(
        "/api/overview/detail?index=" + index
      );
      this.fixedCosts = result.fixedCosts || [];
      this.specialCosts = result.specialCosts || [];
    },
    deleteSpecialCost(id) {
      window.console.log("delete", id);
    }
  }
};
</script>