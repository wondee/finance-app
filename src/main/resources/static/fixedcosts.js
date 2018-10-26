var app = new Vue(

	{
	  el: '#fixedcosts-app',
	  data: {
	    name: "No Name",

	    from: "-",
	    to: "-"
      },

      methods: {
          showModal(index) {

            var row = getElmentById('monthly-table').rows[index];

            this.name = row.cells[0].text();
            this.from = row.cells[2].text();
            this.to = row.cells[3].text();

            $('#limited-modal').modal('show');
          }
      }
  }
);
