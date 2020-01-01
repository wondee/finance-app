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
                <v-card>
                  <v-card-text>
                    <fixed-costs-table
                      :entries="monthly"
                      :cols="monthlyCols"
                      @edit-clicked="openEdit('monthlyEdit', $event)"
                    >
                      <template v-if="$vuetify.breakpoint.smAndDown" v-slot:header>
                        <th />
                      </template>
                      <template v-if="$vuetify.breakpoint.smAndDown" v-slot:content="slotProps">
                        <responsive-date-col :entry="slotProps.entry" />
                      </template>
                    </fixed-costs-table>
                  </v-card-text>
                  <v-card-actions>
                    <v-btn small text @click="openEdit('monthlyEdit')">Neue Kosten Hinzufügen</v-btn>
                    <monthly-cost-edit-form ref="monthlyEdit" />
                  </v-card-actions>
                </v-card>
              </v-tab-item>
              <v-tab>Vierteljährliche Kosten</v-tab>
              <v-tab-item>
                <v-card>
                  <v-card-text>
                    <fixed-costs-table
                      :entries="quaterly"
                      :cols="quaterlyCols"
                      @edit-clicked="openEdit('quaterlyEdit', $event)"
                    />
                  </v-card-text>
                  <v-card-actions>
                    <v-btn small text @click="openEdit('quaterlyEdit')">Neue Kosten Hinzufügen</v-btn>
                    <quaterly-cost-edit-form ref="quaterlyEdit" />
                  </v-card-actions>
                </v-card>
              </v-tab-item>
              <v-tab>Halbjährliche Kosten</v-tab>
              <v-tab-item>
                <v-card>
                  <v-card-text>
                    <fixed-costs-table
                      :entries="halfyearly"
                      :cols="halfyearlyCols"
                      @edit-clicked="openEdit('halfyearly', $event)"
                    />
                  </v-card-text>
                  <v-card-actions>
                    <v-btn small text @click="openEdit('halfyearlyEdit')">Neue Kosten Hinzufügen</v-btn>
                    <halfyearly-cost-edit-form ref="halfyearlyEdit" />
                  </v-card-actions>
                </v-card>
              </v-tab-item>
              <v-tab>Jährliche Kosten</v-tab>
              <v-tab-item>
                <v-card>
                  <v-card-text>
                    <fixed-costs-table
                      :entries="yearly"
                      :cols="yearlyCols"
                      @edit-clicked="openEdit('yearlyEdit', $event)"
                    />
                  </v-card-text>
                  <v-card-actions>
                    <v-btn small text @click="openEdit('yearlyEdit')">Neue Kosten Hinzufügen</v-btn>
                    <yearly-cost-edit-form ref="yearlyEdit" />
                  </v-card-actions>
                </v-card>
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
  monthToString,
  toCurrency,
  toQuaterlyDueDate,
  toHalfyearlyDueDate,
  toMonth
} from "./Utils";

import MonthlyCostEditForm from "./editform/MonthlyCostEditForm";
import QuaterlyCostEditForm from "./editform/QuaterlyCostEditForm";
import HalfyearlyCostEditForm from "./editform/HalfyearlyCostEditForm";
import YearlyCostEditForm from "./editform/YearlyCostEditForm";
import ResponsiveDateCol from './ResponsiveDateCol';

const monthTranformer = m => monthToString(m) || "-";

const defaultCols = [
  { name: "name", label: "Bezeichnung" },
  { name: "amount", label: "Betrag", transformer: toCurrency },
  {
    name: "from",
    label: "Gültig ab",
    transformer: monthTranformer,
    hide: true
  },
  { name: "to", label: "Gültig bis", transformer: monthTranformer, hide: true }
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
  { name: "month", label: "Fällig im", transformer: toMonth }
]);

export default {
  mixins: [LoadablePage],
  components: {
    FixedCostsTable,
    MonthlyCostEditForm,
    QuaterlyCostEditForm,
    HalfyearlyCostEditForm,
    YearlyCostEditForm,
    ResponsiveDateCol
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
  },
  methods: {
    openEdit(name, cost) {
      this.$refs[name].openEdit(cost);
    }
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