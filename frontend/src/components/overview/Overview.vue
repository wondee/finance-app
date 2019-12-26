<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card class="mx-auto" outlined>
          <v-card-text>
            <v-skeleton-loader
              :loading="!loaded"
              type="card"
              transition="scale-transition"
              class="mx-auto"
            >
              <overview-chart :entries="entries" />
            </v-skeleton-loader>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-skeleton-loader
          :loading="!loaded"
          type="card-heading"
          transition="scale-transition"
          class="mx-auto"
        >
          <current-amount :currentAmount="currentAmount" />
        </v-skeleton-loader>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-skeleton-loader
          :loading="!loaded"
          type="table-tbody"
          transition="scale-transition"
          class="mx-auto"
        >
          <overview-table :entries="entries" />
        </v-skeleton-loader>
      </v-col>
    </v-row>
  </v-container>
</template>


<script>
import OverviewChart from "./OverviewChart";
import CurrentAmount from "./CurrentAmount";
import LoadablePage from "../LoadablePage";
import OverviewTable from "./OverviewTable";

export default {
  mixins: [LoadablePage],
  components: {
    OverviewChart,
    CurrentAmount,
    OverviewTable
  },
  data() {
    return {
      entries: null,
      currentAmount: 0,

      config: { showChart: true },
    };
  },
  computed: {
    showChart() {
      return this.loaded && this.config.showChart;
    }
  },
  created: async function() {
    const result = await this.fetchData("/api/overview/all");

    var storageShowChart = localStorage.getItem("finance-config.showChart");
    this.config.showChart = storageShowChart == "true";

    this.entries = result.entries;
    this.currentAmount = result.currentAmount;
  },
  methods: {
    showGraphic: function() {
      this.config.showChart = true;
      localStorage.setItem("finance-config.showChart", "true");
    },
    hideGraphic: function() {
      this.config.showChart = false;
      localStorage.setItem("finance-config.showChart", "false");
    },
  }
};
</script>

<style>
</style>