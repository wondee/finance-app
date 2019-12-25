<template>
  <loadable-page endpoint="/api/overview/all" v-on:loaded="loaded">
    <template v-slot:loading>
      <v-row>
        <v-col>
          <v-skeleton-loader type="card" class="mx-auto"></v-skeleton-loader>
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
        <v-card class="mx-auto" outlined>
          <overview-chart :entries="entries" />
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-card>
          <v-simple-table fixed-header>
            <template v-slot:default>
              <thead>
                <tr>
                  <th>Monat</th>
                  <th>Fixe Kosten</th>
                  <th>Sonder Kosten</th>
                  <th>Saldo</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                <tr :key="index" v-for="(entry, index) in entries">
                  <td>{{ entry | displayMonth }}</td>
                  <td>{{ entry.displayFixedAmount }}</td>
                  <td>{{ entry.displaySpecialAmount | responsive }}</td>
                  <td
                    class="amount"
                    :class="{ 'negative-amount': entry.currentAmount < 0}"
                  >{{ entry.displayCurrentAmount | responsive }}</td>
                  <td align="right">
                    <v-btn icon @click="showModal($event, index)" 
                        v-if="!entry.empty">
                      <v-icon>fa-file-alt</v-icon>
                    </v-btn>
                    <v-btn
                      icon
                      :to="'/specialcosts/add?target=overview&month=' + entry.yearMonth[1] + '&year=' + entry.yearMonth[0]"
                    >
                      <v-icon>fa-plus-square</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
      </v-col>
    </v-row>
    <overview-details :detail="detail" @close="detail = null" />
  </loadable-page>
</template>


<script>
import OverviewChart from "./OverviewChart";
import OverviewDetails from "./OverviewDetails";

export default {
  components: {
    OverviewChart,
    OverviewDetails
  },
  data() {
    return {
      month: "No Month",

      entries: [],

      config: { showChart: true },

      detail: null
    };
  },
  computed: {
    showChart() {
      return this.loaded && this.config.showChart;
    }
  },
  filters: {
    displayMonth: entry =>
      window.innerWidth < 768
        ? entry.yearMonth[1] + " / " + entry.yearMonth[0]
        : entry.displayMonth,

    responsive: function(value) {
      if (value.length > 5 && window.innerWidth < 768) {
        var tmp = value.split(" ");
        var rest = tmp[0].substring(0, tmp[0].length - 2);
        return rest + " T" + tmp[1];
      } else {
        return value;
      }
    }
  },
  methods: {
    loaded: function(result) {
      var storageShowChart = localStorage.getItem("finance-config.showChart");
      this.config.showChart = storageShowChart == "true";

      this.entries = result;
    },
    showGraphic: function() {
      this.config.showChart = true;
      localStorage.setItem("finance-config.showChart", "true");
    },
    hideGraphic: function() {
      this.config.showChart = false;
      localStorage.setItem("finance-config.showChart", "false");
    },

    showModal: async function(event, index) {
      this.detail = { index, month: this.entries[index].displayMonth };
    }
  }
};
</script>

<style>
</style>