/**
 * 
 */

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
                            return label.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " €";
                        }
                    }}]}
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
	    
	    chartData: { 
	    	labels: [], 
	    	data: [] 
	    },
	    loaded: false
	  },
	  created: function() {
		  this.$http.get('/overview/all').then(
			function(response) {
			    // success callback
				  console.log(response.data);
				  
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
		  showModal: function(event) {
			  var link = event.target.parentElement;
			  var index = link.getAttribute('index');
			  this.month = link.getAttribute('month');

			  $('#details-modal').modal('show');
			  $('#load_indicator').show();
			  
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
			  
		  }
	  }
	}

);
