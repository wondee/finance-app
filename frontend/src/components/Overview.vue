<template>
  <loadable-page 
    title="Überblick" 
    endpoint="/api/overview/all"
    v-on:loaded="loaded"
  >
  		<b-row>
			<b-card>
				<OverviewChart :entries="entries"/>
			</b-card>
		</b-row>
    <b-row>
      <b-card>
        <table class="table table-striped">
          <thead>
            <tr>
              <th>Monat</th>
              <th>Fixe Kosten</th>
              <th>Sonder Kosten</th>
              <th>Saldo</th>
              <th />
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
              <td>
                <a
                  href="#"
                  class="icon-link"
                  v-if="!entry.empty"
                  v-on:click="showModal($event, index)"
                >
                  <font-awesome-icon icon="file-alt" />
                </a>
              </td>
              <td>
                <router-link
                  :to="'/specialcosts/add?target=overview&month=' + entry.yearMonth[1] + '&year=' + entry.yearMonth[0]"
                  class="icon-link"
                >
                  <font-awesome-icon icon="plus-square" />
                </router-link>
              </td>
            </tr>
          </tbody>
        </table>
      </b-card>
    </b-row>
    <b-modal ref="details-modal" :title="'Details vom ' + month" hide-footer>
      <img :src="require('../assets/load_indicator.gif')" v-if="!detailsLoaded" />

      <div v-if="specialCosts && specialCosts.length > 0">
        <h4>Sonderkosten:</h4>
        <table class="table table-striped">
          <tbody>
            <tr :key="index" v-for="(cost, index) in specialCosts">
              <td>{{ cost.name }}</td>
              <td>{{ cost.displayAmount }}</td>
              <td>
                <router-link
                  :to="'/fixedcosts/edit?target=overview&id=' + cost.id + '&type=special'"
                  class="icon-link"
                >
									<font-awesome-icon icon="edit" />
                </router-link>
              </td>
              <td>
                <router-link 
                  :to="'/specialcosts/delete?target=overview&id=' + cost.id"
                  class="icon-link"
                >
									<font-awesome-icon icon="trash-alt" />
                </router-link>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <p></p>
      <div v-if="specialCosts && fixedCosts.length > 0">
        <h4>Fixkosten:</h4>
        <table class="table table-striped">
          <tbody>
            <tr :key="index" v-for="(cost, index) in fixedCosts">
              <td>{{ cost.name }}</td>
              <td>{{ cost.displayAmount }}</td>
              <td>{{ cost.displayType }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </b-modal>
  </loadable-page>
</template>


<script>
import OverviewChart from './OverviewChart';

export default {
	components: {
		OverviewChart
	}, 
  data() {
    return {
      month: "No Month",

      specialCosts: Array(),
      fixedCosts: Array(),

      entries: [],

      config: { showChart: true },

      chartData: {
        labels: [],
        data: []
      },
      
      detailsLoaded: false
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
      this.detailsLoaded = false;

      this.month = this.entries[index].displayMonth;

      this.$refs["details-modal"].show();

      this.specialCosts = Array();
      this.fixedCosts = Array();

      const response = await fetch(`/api/overview/detail?index=${index}`);
      const data = await response.json();

      this.specialCosts = data.specialCosts;
      this.fixedCosts = data.fixedCosts;

      this.detailsLoaded = true;
    }
  }
};
</script>

<style>

</style>