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

import com.example.demo.dtos.DailyItemEntryDTO;
import com.example.demo.models.DailyItemEntry;
import com.example.demo.services.AuthService;
import com.example.demo.services.DailyItemEntryService;
import com.example.demo.services.ItemDefinitionService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/dailyItemEntry")
public class DailyItemEntryController {
	DailyItemEntryService dailyItemEntryService;
	AuthenticationManager authenticationManager;
	AuthService authService;
	UserService userService;
	ItemDefinitionService itemDefinitionService;
	
	@Autowired
	public DailyItemEntryController (DailyItemEntryService dailyItemEntryService,
									AuthenticationManager authenticationManager,
									AuthService authService,
									UserService userService,
									ItemDefinitionService itemDefinitionService) {
		this.dailyItemEntryService = dailyItemEntryService;
		this.authenticationManager = authenticationManager;
		this.authService = authService;
		this.userService = userService;
		this.itemDefinitionService = itemDefinitionService;
	}
	
	@PostMapping
	@ResponseBody
	public DailyItemEntryDTO create(@RequestBody DailyItemEntryDTO entry, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : -1;
		int requestedID = entry.getUser_id();
		
		if (requestedID != authorizedID)
			return null;
		
		DailyItemEntry createEntry = entry.getDailyItemEntry(userService, itemDefinitionService);
		entry.setDailyItemEntry( dailyItemEntryService.create(createEntry) );
		return entry;
	}
	
	@GetMapping("/{userId}")
	@ResponseBody
	public List<DailyItemEntryDTO> getByUserID(@PathVariable int userId, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : -1;
		int requestedID = userId;
		
		if (requestedID != authorizedID)
			return null;
		
		List<DailyItemEntryDTO> list = new ArrayList<>();
		for (DailyItemEntry entry : dailyItemEntryService.findByUserId(userId)) {
			DailyItemEntryDTO listEntry = new DailyItemEntryDTO();
			listEntry.setDailyItemEntry(entry);
			list.add(listEntry);
		}
		return list;
	}
	
	@PutMapping
	@ResponseBody
	public DailyItemEntryDTO update(@RequestBody DailyItemEntryDTO entry, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : -1;
		int requestedID = entry.getUser_id();
		
		if (requestedID != authorizedID)
			return null;
		
		DailyItemEntry updateEntry = entry.getDailyItemEntry(userService, itemDefinitionService);
		entry.setDailyItemEntry( dailyItemEntryService.update(updateEntry) );
		return entry;
	}
	
	@DeleteMapping("/{entryId}")
	public void delete(@PathVariable int entryId, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : -1;
		int requestedID = dailyItemEntryService.findById(entryId).get().getUser().getId();
		
		if (requestedID != authorizedID)
			return;
		
		dailyItemEntryService.delete( dailyItemEntryService.findById(entryId).get() );
	}
}
