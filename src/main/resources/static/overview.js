
function toCurrency(string) {
  return string.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " €";
}

Vue.component('line-chart', {
  extends: VueChartJs.Line,
  props: ["data", "options"],
  mounted () {

    this.renderChart({
        labels: this.data.labels,
        datasets: [
          {
            backgroundColor: 'rgb(54, 162, 235)',
            pointRadius: 1,
            fill: false,
            data: this.data.data
          }
        ]
      }, {responsive: true, maintainAspectRatio: false, legend: {
          display: false
      	}, scales: {
            yAxes: [
                {
                    ticks: {
                        callback: function(label, index, labels) {
                           return toCurrency(label);
                        }
                    }}]},
	  tooltips: {
                enabled: true,
                mode: 'single',
                callbacks: {
                    label: function(tooltipItems, data) {
                        return toCurrency(tooltipItems.yLabel);
                    }
                }
            }
      })
  }
})

Vue.filter('responsive', function(value) {
    if (value.length > 5 && $(window).width() < 768) {
      var tmp = value.split(' ');
      var rest = tmp[0].substring(0, tmp[0].length - 4);
      return rest + " T" + tmp[1];
    } else {
        return value;
    }
});

var app = new Vue(

	{
	  el: '#overview-app',
	  data: {
	    month: "No Month",

	    specialCosts: Array(),
	    fixedCosts: Array(),

	    entries: [],

	    config: { showChart: true },

	    chartData: {
	    	labels: [],
	    	data: []
	    },
	    loaded: false
	  },
	  computed: {
		  showChart: function() {
			  return this.loaded && this.config.showChart;
		  }
	  },
	  created: function() {

		  var storageShowChart = localStorage.getItem('finance-config.showChart');

		  this.config.showChart = storageShowChart == 'true';

		  this.$http.get('/overview/all').then(
			function(response) {

			  this.entries = response.data;

			  this.chartData = { labels : [], data : [] };
			  var ctx = this;

			  response.data.forEach(function(entry) {
				  ctx.chartData.data.push(entry.currentAmount);
				  ctx.chartData.labels.push(entry.displayMonth);
			  });

			  this.loaded = true;

			  }, function(response) {
			    // error callback
			  }
			);
	  	},
	  methods: {

		  showGraphic: function() {
			  this.config.showChart = true;
			  localStorage.setItem('finance-config.showChart', 'true');
		  },
		  hideGraphic: function() {
			  this.config.showChart = false;
			  localStorage.setItem('finance-config.showChart', 'false');
		  },
		  showModal: function(event, index) {
			  var link = event.target.parentElement;
			  this.month = this.entries[index].displayMonth;

			  $('#details-modal').modal('show');
			  $('#modal_load_indicator').show();

			  this.specialCosts = Array();
	          this.fixedCosts = Array();

			  // GET /someUrl
			  this.$http.get('/overview/detail', {params: {'index': index}}).then(
				function(response) {

				  this.specialCosts = response.data.specialCosts;
				  this.fixedCosts = response.data.fixedCosts;

				  $('#modal_load_indicator').hide();

			  }, function(response) {
			    // error callback
			  });


			  this.specialCosts = this.entries[index].specialCosts;
	          this.fixedCosts = this.entries[index].fixedCosts;

		  }
	  }
	}

);
