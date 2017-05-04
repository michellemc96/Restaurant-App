$(document).ready(function() {
	
	function load() {
		$.ajax({
			  method: 'GET',
			  url: '/items',
			  dataType: 'json',
			  contentType: 'application/json; charset=utf-8'
			})
			.done(function(response) {
				
				var tbody = $('<tbody></tbody>');
				
				$.each(response, function(i, each) {					
					var tr = $('<tr></tr>')
					       .append('<td>' + each.name + '</td>')
					       .append('<td>' + each.description + '</td>')
					       .append('<td>' + each.cost + '</td>')
					       .append('<td>' + each.icon + '</td>')
					       .append('<td><button class="btn btn-warning" name="edit" data-id="' + each.id + '">Edit</button></td>')
					       .append('<td><button class="btn btn-danger" name="delete" data-id="' + each.id + '">Delete</button></td>');
				       
					tbody.append(tr);
				});
				
				$('table#items').children('tbody').remove();
				$('table#items').append(tbody);
				
			});
	}
	
	$('button[name="add"]').click(function(event) {
		event.preventDefault();
		
		$('form#new')[0].reset();
		$('form#new').show();
		
		$('form#edit')[0].reset();
		$('form#edit').hide();		
	});
	
	$('table#items').on('click', 'button[name="delete"]', function(event) {
		event.preventDefault();
		
		var id = $(this).data('id');
		console.log('delete was clicked for item id = '+id);
		
		if (confirm('Are you sure you want to delete this?')) {
			$.ajax({
				  method: 'DELETE',
				  url: '/items/'+id
				})
				.done(function(response) {
					load();
				});
		}
	});
	
	$('table#items').on('click', 'button[name="edit"]', function(event) {
		event.preventDefault();
		
		var id = $(this).data('id');		
		console.log('edit was clicked for item id = '+id);
		
		$.ajax({
			  method: 'GET',
			  url: '/items/' + id,
			  dataType: 'json',
			  contentType: 'application/json; charset=utf-8'
			})
			.done(function(response) {
				$('form#new')[0].reset();
				$('form#new').hide();
				
				$('form#edit')[0].reset();
				
				$('input[name="id"]').val(response.id);		
				$('input[name="name"]').val(response.name);
				$('input[name="description"]').val(response.description);
				$('input[name="cost"]').val(response.cost);
				$('input[name="icon"]').val(response.icon);
				
				$('form#edit').show();
			});
		
	});	
	
	$('form#new').submit(function(event) {
		event.preventDefault();
				
		var form = $(this).serializeObject();
		console.log('new item submitted');
		console.log(form);

		$.ajax({
			  method: 'POST',
			  url: '/items',
			  dataType: 'json',
			  contentType: 'application/json; charset=utf-8',
			  data: JSON.stringify(form)
			})
			.done(function(response) {							
				$('form#new')[0].reset();
				$('form#new').hide();
				
				load();
			});
		
	});
	
	$('form#edit').submit(function(event) {
		event.preventDefault();
				
		var form = $(this).serializeObject();
		console.log('edits to item id = ' + form.id + ' submitted');
		console.log(form);

		$.ajax({
			  method: 'PUT',
			  url: 'items/' + form.id,
			  dataType: 'json',
			  contentType: 'application/json; charset=utf-8',
			  data: JSON.stringify(form)
			})
			.done(function(response) {							
				$('form#edit')[0].reset();
				$('form#edit').hide();
				
				load();
			});
		
	});
	
	load();
		
});