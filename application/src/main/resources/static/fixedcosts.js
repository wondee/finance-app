var app = new Vue({
	  el: '#fixedcosts-app',
	  data: {
	    entryName: "No Name",
	    
	    deletionDialogConfig: {
	    	entryName: "No Name",
	    	href: ""
	    },
	    
	    from: "-",
	    to: "-"
	    
      },

      methods: {
          showModal: function(index, tableId) {

            var row =  getRow(tableId, index);

            this.entryName = row[0].innerHTML;
            
            // dirty hack... future me will hate me for that!
            var adjustment = 1;
            if (tableId.startsWith('monthly')) adjustment = 0;
            
            this.from = row[2 + adjustment].innerHTML;
            this.to = row[3 + adjustment].innerHTML;

            $('#limited-modal').modal('show');
          },
          
          showConfirmDelete: function(index, type) {
        	this.deletionDialogConfig = initDeletionDialog(type + '-table', index, 
        			'/fixedcosts/delete?type=' + type + '&target=fixed&id=' + index);
          }
      }
  }
);
