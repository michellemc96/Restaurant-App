$(document).ready(function() {
	
	function load() {
		
		var newOrders = $('<span></span>').append('<h1>New Food Orders</h1>');
		var nNew = 0;
		
		var cookingOrders = $('<span></span>').append('<h1>Cooking Orders</h1>');
		var nCooking = 0;
		
		$.ajax({
			  method: 'GET',
			  url: '/orders/?status=NEW&status=COOKING',
			  dataType: 'json',
			  contentType: 'application/json; charset=utf-8'
			})
			.done(function(response) {
				
				$.each(response, function(i, each) {
					
					var div = $('<div class="box"></div>');
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
							  + (each.status == 'NEW' ? '<button name="start" data-id="'+each.id+'" class="btn btn-success center-block">Start</button>' : '')		 	      
							  + (each.status == 'COOKING' ? '<button name="finish" data-id="'+each.id+'" class="btn btn-danger center-block">Finish</button>' : '')
							  + '</div>'
							  + '<br>';
					div.html(html);
					if (each.status == 'NEW') {
						newOrders.append(div);
						nNew = nNew + 1;
						
					}
					else if (each.status == 'COOKING') {
						cookingOrders.append(div);
						nCooking = nCooking + 1;
					}
					
				});
				if (nNew <= 0) {			
					newOrders.append($('<h2>Yay! There are no orders for you to cook!</h2>'));
				}

				if (nCooking <= 0) {
					cookingOrders.append($('<h2>There is nothing cooking -- go outside and take a break!</h2>'));
				}
		});
		
		
		$('div#new').empty().html(newOrders);
		$('div#cooking').empty().html(cookingOrders);	
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
	
	
	$('div#new').on('click', 'button[name="start"]', function(event) {
		event.preventDefault();	
		var id = $(this).data('id');
		var order = find(id);
		order.status = 'COOKING';
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
				
	$('div#cooking').on('click', 'button[name="finish"]', function(event) {
		event.preventDefault();
		var id = $(this).data('id');
		var order = find(id);
		order.status = 'READY';
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