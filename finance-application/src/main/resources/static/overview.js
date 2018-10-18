/**
 * 
 */

var app = new Vue(
		
	{
	  el: '#overview-app',
	  data: {
	    month: "No Month",
	    
	    specialCosts: Array(),
	    fixedCosts: Array()
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
			    // success callback
				  console.log(response.data);
				  
				  this.specialCosts = response.data.specialCosts;
				  this.fixedCosts = response.data.fixedCosts;
				  
				  $('#load_indicator').hide();
				  
			  }, function(response) {
			    // error callback
			  });
			  
		  }
	  }
	}

);
