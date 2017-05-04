package io.github.michellemc96;

import java.util.List;

public class Order {
	
	private long id;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String tel;
	private double cost;
	private String status;
	private String email;
	private String payment;
	private boolean paid;
	private List<OrderItem> items;
	public Order() {}
	
	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public void setAddress2(String address2 ) {
		this.address2 = address2;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getPayment() {
		return payment;
	}
	
	public void setPayment(String payment){
		this.payment = payment;
	}
	
	public boolean getPaid() {
		return paid;
	}
	
	public void setPaid(boolean paid){
		this.paid = paid;
	}
	
	
}
