package com.itemsharing.itemservice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itemsharing.itemservice.model.Item;
import com.itemsharing.itemservice.model.User;
import com.itemsharing.itemservice.service.ItemService;
import com.itemsharing.itemservice.service.UserService;

@SpringBootApplication
public class ItemServiceApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;
	
	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		User user = userService.findByUsername("jadams");
		
		Item item1 = new Item();
		item1.setName("Item1");
		item1.setItemCondition("New");
		item1.setStatus("active");
		item1.setAddDate(new Date());
		item1.setDescription("This is an item description.");
		item1.setUser(user);
		
		itemService.addItemByUser(item1, user.getUsername());
		
		Item item2 = new Item();
		item2.setName("Item2");
		item2.setItemCondition("Used");
		item2.setStatus("Inactive");
		item2.setAddDate(new Date());
		item2.setDescription("This is an item description for Item2.");
		item2.setUser(user);
		
		itemService.addItemByUser(item2, user.getUsername());
	}
	
}
