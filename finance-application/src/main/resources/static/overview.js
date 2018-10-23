
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

var app = new Vue(
		
	{
	  el: '#overview-app',
	  data: {
	    month: "No Month",
	    
	    specialCosts: Array(),
	    fixedCosts: Array(),
	    
	    entries: [],
	    
	    chartData: { 
	    	labels: [], 
	    	data: [] 
	    },
	    loaded: false
	  },
	  created: function() {
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
			  })
	  	},
	  methods: {
		  showModal: function(event, index) {
			  var link = event.target.parentElement;
			  this.month = this.entries[index].displayMonth;

			  $('#details-modal').modal('show');
			  
			  this.specialCosts = this.entries[index].specialCosts;
	          this.fixedCosts = this.entries[index].fixedCosts;
			  
		  },
		  isNegative: function(value) { return value < 0; }
	  }
	}

);
