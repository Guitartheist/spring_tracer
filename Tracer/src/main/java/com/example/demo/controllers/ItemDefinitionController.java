package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.ItemDefinitionDTO;
import com.example.demo.models.ItemDefinition;
import com.example.demo.services.AuthService;
import com.example.demo.services.ItemDefinitionService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/itemDefinition")
public class ItemDefinitionController {
	ItemDefinitionService itemDefinitionService;
	AuthenticationManager authenticationManager;
	AuthService authService;
	UserService userService;
	
	@Autowired
	public ItemDefinitionController(ItemDefinitionService itemDefinitionService,
									AuthenticationManager authenticationManager,
									AuthService authService,
									UserService userService) {
		this.itemDefinitionService = itemDefinitionService;
		this.authenticationManager = authenticationManager;
		this.authService = authService;
		this.userService = userService;
	}
	
	@PostMapping
	@ResponseBody
	public ItemDefinitionDTO create(@RequestBody ItemDefinitionDTO item, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : -1;
		int requestedID = item.getCreator_id();
		
		if (requestedID != authorizedID)
			return null;
		
		ItemDefinition createItem = item.getItemDefinition(userService);
		item.setItemDefinition(
				itemDefinitionService.create(createItem));
		return item;
	}
	
	@GetMapping("/{userId}")
	@ResponseBody
	public List<ItemDefinitionDTO> getByUserID(@PathVariable int userId, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : -1;
		int requestedID = userId;
		
		if (requestedID != authorizedID)
			return null;
		
		List<ItemDefinitionDTO> list = new ArrayList<>();
		for (ItemDefinition item : itemDefinitionService.findByUserId(userId)) {
			ItemDefinitionDTO listItem = new ItemDefinitionDTO();
			listItem.setItemDefinition(item);
			list.add(listItem);
		}
		return list;
	}
	
	@PutMapping
	@ResponseBody
	public ItemDefinitionDTO update(@RequestBody ItemDefinitionDTO item, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : -1;
		int requestedID = item.getCreator_id();
		
		if (requestedID != authorizedID)
			return null;
		
		ItemDefinition updateItem = item.getItemDefinition(userService);
		item.setItemDefinition(
				itemDefinitionService.update(updateItem));
		return item;
	}
	
	@DeleteMapping("/{entryId}")
	public void delete(@PathVariable int entryId, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : -1;
		int requestedID = itemDefinitionService.findById(entryId).get().getCreator().getId();
		
		if (requestedID != authorizedID)
			return;
		
		itemDefinitionService.delete( itemDefinitionService.findById(entryId).get() );
	}
}
