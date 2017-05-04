package io.github.michellemc96;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemDAO service;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Item> list() {
		return service.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Item view(@PathVariable("id") long id) throws FileNotFoundException {
		Item item = service.find(id);
		if (item == null) throw new FileNotFoundException("item id = " + id + " was not found in the database");
		
		return item;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Item create(@RequestBody Item item) throws FileNotFoundException {		
		service.create(item);
		
		return item;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Item update(@PathVariable("id") long id, @RequestBody Item update) throws FileNotFoundException {
		Item item = service.find(id);
		if (item == null) throw new FileNotFoundException("item id = " + id + " was not found in the database");

		item.setName(update.getName());
		item.setDescription(update.getDescription());
		item.setCost(update.getCost());
		item.setIcon(update.getIcon());
		service.update(item);
		
		return item;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") long id) throws FileNotFoundException {
		Item item = service.find(id);
		if (item == null) throw new FileNotFoundException("item id = " + id + " was not found in the database");
		
		service.delete(item);
	}


}
