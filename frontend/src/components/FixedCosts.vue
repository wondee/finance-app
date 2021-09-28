<template>
  <v-container>
    <v-row>
      <v-col>
        <v-skeleton-loader
          type="card-heading"
          :loading="!loaded"
          transition="scale-transition"
          class="mx-auto"
        >
          <v-banner sticky icon="fa-wallet" elevation="4">
            Aktuelle Bilanz (pro Monat):
            <strong
              :class="{ red : currentBalance < 0 }"
            >{{ currentBalanceDisplay }}</strong>
          </v-banner>
        </v-skeleton-loader>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-skeleton-loader
          type="table-tbody"
          :loading="!loaded"
          transition="scale-transition"
          class="mx-auto"
        >
          <v-card no-body>
            <v-tabs v-model="tab" grow>
              <v-tabs-slider />
              <v-tab>Monatliche Kosten</v-tab>
              <v-tab-item>
                <fixed-costs-table
                  :entries="monthly"
                  :cols="monthlyCols"
                  formComponent="monthly-cost-edit-form"
                />
              </v-tab-item>
              <v-tab>Vierteljährliche Kosten</v-tab>
              <v-tab-item>
                <fixed-costs-table
                  :entries="quaterly"
                  :cols="quaterlyCols"
                  formComponent="quaterly-cost-edit-form"
                />
              </v-tab-item>
              <v-tab>Halbjährliche Kosten</v-tab>
              <v-tab-item>
                <fixed-costs-table
                  :entries="halfyearly"
                  :cols="halfyearlyCols"
                  formComponent="halfyearly-cost-edit-form"
                />
              </v-tab-item>
              <v-tab>Jährliche Kosten</v-tab>
              <v-tab-item>
                <fixed-costs-table
                  :entries="yearly"
                  :cols="yearlyCols"
                  formComponent="yearly-cost-edit-form"
                />
              </v-tab-item>
            </v-tabs>
          </v-card>
        </v-skeleton-loader>
      </v-col>
    </v-row>
  </v-container>
</template>


<script>
import FixedCostsTable from "./FixedCostTable";
import LoadablePage from "./LoadablePage";
import {
  displayMonth,
  toCurrency,
  toQuaterlyDueDate,
  toHalfyearlyDueDate,
  toMonth
} from "./Utils";

const defaultCols = [
  { name: "name", label: "Bezeichnung" },
  { name: "amount", label: "Betrag", transformer: toCurrency },
  {
    name: "from",
    label: "Gültig ab",
    transformer: displayMonth,
    hide: true
  },
  { name: "to", label: "Gültig bis", transformer: displayMonth, hide: true }
];

function cols(additionalCols = false) {
  if (!additionalCols) {
    return defaultCols;
  }
  const cols = [...defaultCols];
  cols.splice(1, 0, ...additionalCols);
  return cols;
}

const quaterlyCols = cols([
  { name: "dueMonth", label: "Fällig in", transformer: toQuaterlyDueDate }
]);

const halfyearlyCols = cols([
  { name: "dueMonth", label: "Fällig in", transformer: toHalfyearlyDueDate }
]);

const yearlyCols = cols([
  { name: "dueMonth", label: "Fällig im", transformer: toMonth }
]);

export default {
  mixins: [LoadablePage],
  components: {
    FixedCostsTable
  },
  data() {
    return {
      tab: null,

      monthly: [],
      quaterly: [],
      halfyearly: [],
      yearly: [],

      currentBalance: -1,

      monthlyCols: cols(),
      quaterlyCols,
      halfyearlyCols,
      yearlyCols
    };
  },
  computed: {
    currentBalanceDisplay() {
      return `${this.currentBalance} €`;
    }
  },
  created: async function() {
    const data = await this.fetchData("/api/costs");
    this.monthly = data.monthly;
    this.quaterly = data.quaterly;
    this.halfyearly = data.halfyearly;
    this.yearly = data.yearly;

    this.currentBalance = data.currentBalance;
  }
};
</script>

<style scoped>
strong.red {
  color: red;
}
.tabs {
  width: 100%;
}
</style>