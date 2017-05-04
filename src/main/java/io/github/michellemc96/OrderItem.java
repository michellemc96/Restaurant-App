package io.github.michellemc96;

public class OrderItem {

	private long id;
	
	private Item item;
	private long quantity;
	
	public OrderItem() {}
	
	public OrderItem(Item item, long quantity) {		
		this.item = item;
		this.quantity = quantity;		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
}
