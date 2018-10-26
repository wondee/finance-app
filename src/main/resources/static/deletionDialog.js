function getRow(tableId, index) {
    var row = document.getElementById(tableId).rows[index + 1];
    return row.cells;
}


function initDeletionDialog(tableId, index, href) {
	var row =  getRow(tableId, index);
	
	this.entryName = row[0].innerHTML;
	
	$('#confirm-delete').modal('show');
	
	console.log(entryName);
	
	return { entryName: entryName, href: href };
}