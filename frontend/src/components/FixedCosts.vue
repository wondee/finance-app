<template>
  <loadable-page 
    title="Fixkosten Verwaltung" 
    endpoint="/api/costs"
    v-on:loaded="loaded"
  >
    <b-row>
      <b-card>
        <p>
          Aktuelle Bilanz (pro Monat):
          <strong
            :class="{ red : currentBalance < 0 }"
          >{{ currentBalanceDisplay }}</strong>
        </p>
      </b-card>
    </b-row>
    <b-row>
      <b-card no-body>
        <b-tabs card>
          <b-tab title="Monatliche Kosten">
            <FixedCostsTable :entries="monthly"/>
            <b-button variant="link" @click="show('monthlyEdit')">Neuen monatliche Fixkosten erfassen</b-button>
            <CostEditForm ref="monthlyEdit" title="Monatliche Kosten "/>
          </b-tab>
          <b-tab title="Vierteljährliche Kosten">
            <FixedCostsTable :entries="quaterly" :additionalCols="quaterlyCols"/>
            <a href="#">Neuen vierteljährliche Fixkosten erfassen</a>
          </b-tab>
          <b-tab title="Halbjährliche Kosten">
            <FixedCostsTable :entries="halfyearly" :additionalCols="halfyearlyCols"/>
            <a href="#">Neuen halbjährlige Fixkosten erfassen</a>
          </b-tab>
          <b-tab title="Jährliche Kosten">
            <FixedCostsTable :entries="yearly" :additionalCols="yearlyCols"/>
            <a href="#">Neuen jährliche Fixkosten erfassen</a>
          </b-tab>
        </b-tabs>
      </b-card>
    </b-row>
  </loadable-page>
</template>


<script>
import FixedCostsTable from './FixedCostTable';
import CostEditForm from './CostEditForm';
import { toQuaterlyDueDate, toHalfyearlyDueDate, toMonth } from './Utils';

const quaterlyCols = [
  { name: "dueMonth", label: "Fällig in", transformer: toQuaterlyDueDate }
]

const halfyearlyCols = [
  { name: "dueMonth", label: "Fällig in", transformer: toHalfyearlyDueDate }
]

const yearlyCols = [
  { name: "month", label: "Fällig im", transformer: toMonth }
]


export default {
  components: {
    FixedCostsTable,
    CostEditForm
  },
  data() {
    return {
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
    show(ref) {
      this.$refs[ref].showModal();
    }
  }
};
</script>

<style scoped>
strong.red {
  color: red;
}
.tabs {
  width: 100%
}
</style>