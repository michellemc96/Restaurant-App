$(document).ready(function() {
	
	function load() {
	
	var readyOrders = $('<span></span>').append('<h1>Ready Orders</h1>');
		
		$.ajax({
			  method: 'GET',
			  url: '/orders?status=READY',
			  dataType: 'json',
			  contentType: 'application/json; charset=utf-8'
			})
			.done(function(response) {
				
				$.each(response, function(i, each) {
					
					var div = $('<div class="box">');
					 var html = '<h2>#'+each.id+'</h2>'
					 	  	  + '<table class="table table=striped">'
							  + '  <thead>'
							  + '    <tr>'
							  + '	 	<th>Quantity</th>'
							  + '        <th>Name</th>'
							  + '    </tr>'
							  + '  </thead>'
							  + '  <tbody>';
					 
			 $.each(each.items, function(e, item) {
				 html = html + '<tr>'
				  + '<td>'+item.quantity+'</td>' 
		 	      + '<td>'+item.item.name+'</td>'
		 	      + '</tr>';
			 });
					 	      
					
				  html = html + '  </tbody>'
							  +	'</table>'
							  + '<button name="ready" data-id="'+each.id+'" class="btn btn-success center-block">Fully Finished</button>'
							  + '</div>'
							  + '<br>';
				 div.html(html);
					
					if (each.status == 'READY') {
						readyOrders.append(div);
					}
				
				});
				
			});
					$('div#ready').empty().html(readyOrders);
	}
	function find(id) {
		var order;
		
		$.ajax({
			  async: false,
			  method: 'GET',
			  url: '/orders/' + id,
			  dataType: 'json',
			  contentType: 'application/json; charset=utf-8'
			})
			.done(function(response) {
				order = response;
			});
		
		return order;

	}
	
		
		
				$('div#ready').on('click', 'button[name="ready"]', function(){
					event.preventDefault();
					var id = $(this).data('id');
					var order = find(id);
					order.status = 'COMPLETE';
					console.log(order);
					$.ajax({
						method: 'PUT',
						url: '/orders/' + id, 
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						data: JSON.stringify(order)
				    })
				    
				      .done(function(response) {
					   load();
				      });
			   });
				
		load();
});