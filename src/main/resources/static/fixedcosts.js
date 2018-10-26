function getRow(tableId, index) {
    var row = document.getElementById(tableId).rows[index + 1];
    return row.cells;
}

var app = new Vue(

	{
	  el: '#fixedcosts-app',
	  data: {
	    entryName: "No Name",
	    
	    index: -1,
	    type: null,
	    
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
            
            var row =  getRow(type + '-table', index);
            this.index = index;
            this.type = type;
            this.entryName = row[0].innerHTML;
            
            $('#confirm-delete').modal('show');
            
          }
      }
  }
);
