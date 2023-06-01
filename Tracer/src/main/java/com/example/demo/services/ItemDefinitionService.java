package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ItemDefinition;
import com.example.demo.repos.ItemDefinitionRepo;

@Service
public class ItemDefinitionService {
	private ItemDefinitionRepo itemDefinitionRepo;
	
	@Autowired
	public ItemDefinitionService(ItemDefinitionRepo itemDefinitionRepo) {
		this.itemDefinitionRepo = itemDefinitionRepo;
	}
	
	public Optional<ItemDefinition> findById(int id) {
		return itemDefinitionRepo.findById(id);
	}
	
	
	public ItemDefinition create(ItemDefinition item) {
		return itemDefinitionRepo.save(item);
	}
	
	public List<ItemDefinition> findByUserId(int id){
		return itemDefinitionRepo.findItemDefinitionsByUserID(id);
	}
	
	public ItemDefinition update(ItemDefinition item) {
		return itemDefinitionRepo.save(item);
	}
	
	public void delete(ItemDefinition item) {
		itemDefinitionRepo.delete(item);
	}
}
