package io.github.michellemc96;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderDAO service;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Order> list(@RequestParam(name = "status", required = false) String[] statuses)  {
		if(statuses != null) {
			return service.findStatus(statuses);
		}
		return service.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Order view(@PathVariable("id") long id) throws FileNotFoundException {
		Order order = service.find(id);
		if (order == null) throw new FileNotFoundException("order id = " + id + " was not found in the database");
		
		return order;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Order create(@RequestBody Order order) throws FileNotFoundException {		
		
		order.setStatus("NEW");
		service.create(order);
		
		return order;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Order update(@PathVariable("id") long id, @RequestBody Order update) throws FileNotFoundException {
		Order order = service.find(id);
		if (order == null) throw new FileNotFoundException("order id = " + id + " was not found in the database");

		order.setName(update.getName());
		order.setAddress1(update.getAddress1());
		order.setAddress2(update.getAddress2());
		order.setCity(update.getCity());
		order.setState(update.getState());
		order.setTel(update.getTel());
		order.setCost(update.getCost());
		order.setStatus(update.getStatus());
		order.setPaid(update.getPaid());
		order.setEmail(update.getEmail());
		order.setPayment(update.getPayment());
		order.setItems(update.getItems());
		
		service.update(order);
		
		return order;
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") long id) throws FileNotFoundException {
		Order order = service.find(id);
		if (order == null) throw new FileNotFoundException("order id = " + id + " was not found in the database");
		
		service.delete(order);
	}
	
}
/* request parameter in a springboot rest controller, modify sql to look for orders of a certain status*/
