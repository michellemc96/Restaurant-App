$(document).ready(function(){
	
		$('input[name="continue"]').click(function(event){
			event.preventDefault();
			$('#step1').hide();
			$('#step2').show();
			$('#step3').hide();
			$('#step4').hide();			
		});
		
		$('input[name="cancel"]').click(function(event){
			event.preventDefault();

			$('#step1').show();
			$('#step2').hide();
			$('#step3').hide();
			$('#step4').hide();			
		});
		
		$('input[name="confOrder"]').click(function(event){
			event.preventDefault();
			$('#step1').hide();
			$('#step2').hide();
			$('#step3').show();	
			$('#step4').hide();	
		});
		$('input[name="editOrder"]').click(function(event){
			event.preventDefault();
			$('#step1').show();
			$('#step2').hide();
			$('#step3').hide();	
			$('#step4').hide();	
			$('input[name="confOrder"]').prop('disabled', false);
		});
		$('input[name="clear"]').click(function(event){
			event.preventDefault();
			$('div#shopping-cart-empty').show();
			$('input[name="confOrder"]').prop('disabled', true);
			$('form#order input[name="items"]').remove();
			buildShoppingCart();
			updateShoppingCartTotal();
		});
		$('input[name="home"]').click(function(event){
			event.preventDefault();
			$('#step1').show();
			$('#step2').hide();
			$('#step3').hide();	
			$('#step4').hide();	
			$('form#order input[name="items"]').remove();
			buildShoppingCart();
			updateShoppingCartTotal();
			
		});
		
		
		$('span#menu').on('click', 'input[name="add"]', function(event) {
			event.preventDefault();
			var item = $(this).data('item');
			
			var orderItem = convertItemToOrderItem(item);
			
			$('form#order').append($('<input type="hidden" name="items" value=\''+JSON.stringify(orderItem)+'\' />'));
			
			updateShoppingCartTotal();
		});
		
		$('div#shopping-cart-items').on('click', 'input[name="remove"]', function(event) {
			event.preventDefault();
			var item = $(this).data('item');

			$('form#order input[value=\''+JSON.stringify(item)+'\']').first().remove();
			
			updateShoppingCartTotal();
		});		
		
		
		$('a#shopping-cart-button').click(function (event) {
			event.preventDefault();
						
			$('#step1').hide();
			$('#step2').show();
			$('#step3').hide();
			$('#step4').hide();
		});
		
		
		function convertItemToOrderItem(item) {
			var orderItem = {};
			orderItem.id = 0;
			orderItem.item = item;
			orderItem.quantity = 1;
			return orderItem;
		}
				
		function updateShoppingCartTotal() {			
			var count = $('form#order input[name="items"]').size();			
			$('span#items-count').html(count);
			
			buildShoppingCart();
		}
		
		function buildMenu() {

			$.ajax({
				  method: 'GET',
				  url: '/items',
				  dataType: 'json',
				  contentType: 'application/json; charset=utf-8'
				})
				.done(function(response) {						
						var span = $('span#menu');
					
						$.each(response, function(i, each) {
							
							var div = $('<div class="row menu_item">'
								    + '    <div class="icon col-md-2">'
									+ '       <img src="/images/'+each.icon+'" alt="burger" class="food">'
								    + '    </div>'
								    + '    <div class="description col-md-7">'
								    + '      <h2>'+each.name+'</h2>'
								    + '      <p>'+each.description+'</p>'
								    + '   </div>'
								    + '   <div class="price col-md-1">$'+each.cost+'</div>'								
								    + '  <div class="button col-md-2">'
								    + '     <input type="button" name="add" class="btn btn-default btn-lg" value="Add Item" data-item=\''+JSON.stringify(each)+'\'/>'
								    + '  </div>'
								    + '</div>');
							
							span.append(div);
						});
				});
		}
				
		
		function buildShoppingCart() {
			var shoppingCart = $('#shopping-cart-items');
			
			shoppingCart.empty();
			
			var total = 0.00;
			
			var items = $('form#order input[name="items"]');
			
			if (items.size() == 0) {
				shoppingCart.html('<p>Your basket is empty!</p>');
			}
			else {
					
				$.each(items, function(i, each) {
					
					var item = JSON.parse(each.value);
					
					var div = $('<div class="row menu_item">'
						    + '    <div class="icon col-md-2">'
							+ '       <img src="/images/'+item.item.icon+'" alt="burger" class="food">'
						    + '    </div>'
						    + '    <div class="description col-md-7">'
						    + '      <h2>'+item.item.name+'</h2>'
						    + '      <p>'+item.item.description+'</p>'
						    + '   </div>'
						    + '   <div class="price col-md-1">$'+item.item.cost+'</div>'								
						    + '  <div class="button col-md-2">'
						    + '     <input type="button" name="remove" class="btn btn-danger btn-lg" value="Remove Item" data-item=\''+each.value+'\'/>'
						    + '  </div>'
						    + '</div>');
					
					total += item.item.cost;
					
					shoppingCart.append(div);
				});
			}
			
			$('span#shopping-cart-total').html('$'+total);			
		}
		
		buildMenu();
					
		$('form#order').submit(function(event) {
			event.preventDefault();
					
			var form = $(this).serializeObject();
			
			var items = [];
			if(Array.isArray(form.items)) {
			$.each(form.items, function(i, each) {
				items[i] = JSON.parse(each);
			}); 
			}else{
				items[0] = JSON.parse(form.items);
			}
			
			form.items = items;
			
			console.log('new order submitted');
			console.log(form);

			$.ajax({
				  method: 'POST',
				  url: '/orders',
				  dataType: 'json',
				  contentType: 'application/json; charset=utf-8',
				  data: JSON.stringify(form)
				})
				.done(function(response) {							
					$('form#order')[0].reset();
					$('form#order').hide();
					$('div#step3').hide();
					$('div#step4').show();
					
					
				});
			
		});
});