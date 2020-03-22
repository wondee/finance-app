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
              <cost-table
                :entries="entries"
                :cols="cols"
                @edit-clicked="openEdit($event)"
              >
                <template v-slot:edit-button="slotProps">
                  <special-cost-form :cost="slotProps.entry"/>    
                </template>
              </cost-table>
            </v-card-text>
            <v-card-actions>
              <special-cost-form btn-text="Neue Sonderkosten Hinzufügen" />
            </v-card-actions>
          </v-card>
        </v-skeleton-loader>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import LoadablePage from "./LoadablePage";
import CostTable from "./CostTable";

import {
  CommonForm,
  toCurrency,
  monthlyCostToForm,
  displayMonth
} from "./Utils";
import SpecialCostForm from './editform/SpecialCostForm.vue';

const cols = [
  { name: "name", label: "Bezeichnung" },
  { name: "amount", label: "Betrag", transformer: toCurrency },
  { name: "dueDate", label: "Fällig am", transformer: displayMonth }
];

const costToForm = cost => {
  const form = monthlyCostToForm(cost);

  return !cost
    ? {
        ...form,
        dueYearMonth: null
      }
    : {
        ...form,
        dueYearMonth: cost.dueYearMonth
      };
};

export default {
  mixins: [LoadablePage, CommonForm(costToForm)],
  components: {
    CostTable,
    SpecialCostForm
  },
  data() {
    return {
      entries: [],
      cols
    };
  },
  created: async function() {
    const data = await this.fetchData("/api/specialcosts");
    this.entries = data;
  }
};
</script>