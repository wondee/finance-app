var app = new Vue(

	{
	  el: '#fixedcosts-app',
	  data: {
	    name: "No Name",

	    from: "-",
	    to: "-"
      },

      methods: {
          showModal(index, tableId) {

            var row = document.getElementById(tableId).rows[index + 1];

            this.name = row.cells[0].innerHTML;
            this.from = row.cells[2].innerHTML;
            this.to = row.cells[3].innerHTML;

            $('#limited-modal').modal('show');
          }
      }
  }
);
