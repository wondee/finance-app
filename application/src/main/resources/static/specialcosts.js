var app = new Vue({
	  el: '#specialcosts-app',
	  data: {
	    
		  deletionDialogConfig: {
	    	entryName: "No Name",
	    	href: ""
	    }
	    
      },
      methods: {
          showConfirmDelete: function(index) {
        	this.deletionDialogConfig = initDeletionDialog('special-table', index,
        			'/specialcosts/delete?target=special&id=' + index);
          }
      }
  }
);
