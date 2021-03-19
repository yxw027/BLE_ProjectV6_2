$(function() {
    $('#easypiechart-green').easyPieChart({
        scaleColor: false,
        barColor: '#1ebfae'
    });
});

$(function() {
    $('#easypiechart-yellow').easyPieChart({
        scaleColor: false,
        barColor: '#ffb53e'
    });
});

$(function() {
    $('#easypiechart-red').easyPieChart({
        scaleColor: false,
        barColor: '#f9243f'
    });
});

$(function() {
   $('#easypiechart-blue').easyPieChart({
       scaleColor: false,
       barColor: '#30a5ff'
   });
});
 

function filterTable(rr) {
	//$("#hide").click(function() {

	var dataTable = $('#dataTables-example').DataTable();
	
	    $.fn.dataTable.ext.search.pop();
	    dataTable.draw();
	
	
	$.fn.dataTable.ext.search
			.push(function(settings, data, dataIndex) {
				if (rr == 'Low') {
					return $(dataTable.row(dataIndex).node()).attr('data-user') == 'lowR';
				}else if(rr=='High'){
					return $(dataTable.row(dataIndex).node()).attr('data-user') == 'highR';
				}else if(rr=='Normal'){
					return $(dataTable.row(dataIndex).node()).attr('data-user') == 'normalR';
				}else if(rr=='Slight'){
					return $(dataTable.row(dataIndex).node()).attr('data-user') == 'slightR';
				}else{
					$.fn.dataTable.ext.search.pop();
				    dataTable.draw();
				}
			});
	dataTable.draw();
}