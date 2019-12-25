<template>
  <loadable-page endpoint="/api/costs" v-on:loaded="loaded">
    <template v-slot:loading>
      <v-row>
        <v-col>
          <v-skeleton-loader type="card-heading" class="mx-auto"></v-skeleton-loader>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-skeleton-loader type="table-tbody" class="mx-auto"></v-skeleton-loader>
        </v-col>
      </v-row>
    </template>
    <v-row>
      <v-col>
        <v-banner sticky icon="fa-wallet" elevation="4">
          Aktuelle Bilanz (pro Monat):
          <strong
            :class="{ red : currentBalance < 0 }"
          >{{ currentBalanceDisplay }}</strong>
        </v-banner>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-card no-body>
          <v-tabs v-model="tab" grow>
            <v-tabs-slider />
            <v-tab>Monatliche Kosten</v-tab>
            <v-tab-item>
              <v-card>
                <v-card-text>
                  <fixed-costs-table :entries="monthly" @edit-clicked="openEdit('monthly', $event)"/>
                </v-card-text>
                <v-card-actions>
                  <v-btn small text @click="openEdit('monthly')">Neue Kosten Hinzufügen</v-btn>
                </v-card-actions>
              </v-card>
            </v-tab-item>
            <v-tab>Vierteljährliche Kosten</v-tab>
            <v-tab-item>
              <v-card>
                <v-card-text>
                  <fixed-costs-table :entries="quaterly" :additionalCols="quaterlyCols" @edit-clicked="openEdit('quaterly', $event)" />
                </v-card-text>
                <v-card-actions>
                  <v-btn small text @click="show('quaterlyEdit')">Neue Kosten Hinzufügen</v-btn>
                </v-card-actions>
              </v-card>
            </v-tab-item>
            <v-tab>Halbjährliche Kosten</v-tab>
            <v-tab-item>
              <v-card>
                <v-card-text>
                  <fixed-costs-table :entries="halfyearly" :additionalCols="halfyearlyCols" @edit-clicked="openEdit('halfyearly', $event)" />
                </v-card-text>
                <v-card-actions>
                  <v-btn small text @click="show('halfyearlyEdit')">Neue Kosten Hinzufügen</v-btn>
                </v-card-actions>
              </v-card>
            </v-tab-item>
            <v-tab>Jährliche Kosten</v-tab>
            <v-tab-item>
              <v-card>
                <v-card-text>
                  <fixed-costs-table :entries="yearly" :additionalCols="yearlyCols" @edit-clicked="openEdit('yearly', $event)" />
                </v-card-text>
                <v-card-actions>
                  <v-btn small text @click="show('monthlyEdit')">Neue Kosten Hinzufügen</v-btn>
                </v-card-actions>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </v-card>
      </v-col>
    </v-row>
    <cost-edit-form ref="editForm" />
  </loadable-page>
</template>


<script>
import FixedCostsTable from "./FixedCostTable";
import CostEditForm from "./CostEditForm";
import { toQuaterlyDueDate, toHalfyearlyDueDate, toMonth } from "./Utils";

const quaterlyCols = [
  { name: "dueMonth", label: "Fällig in", transformer: toQuaterlyDueDate }
];

const halfyearlyCols = [
  { name: "dueMonth", label: "Fällig in", transformer: toHalfyearlyDueDate }
];

const yearlyCols = [
  { name: "month", label: "Fällig im", transformer: toMonth }
];

export default {
  components: {
    FixedCostsTable,
    CostEditForm
  },
  data() {
    return {
      tab: null,

      monthly: [],
      quaterly: [],
      halfyearly: [],
      yearly: [],

      currentBalance: -1,

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
  methods: {
    loaded(data) {
      this.monthly = data.monthly;
      this.quaterly = data.quaterly;
      this.halfyearly = data.halfyearly;
      this.yearly = data.yearly;

      this.currentBalance = data.currentBalance;
    },
    openEdit(type, cost) {
      this.$refs['editForm'].openEdit(type, cost);
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