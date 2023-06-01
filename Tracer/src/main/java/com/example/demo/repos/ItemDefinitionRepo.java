package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ItemDefinition;

@Repository
public interface ItemDefinitionRepo extends CrudRepository<ItemDefinition, Integer> {
	@Query("from ItemDefinition i where i.creator.id = :id")
	List<ItemDefinition> findItemDefinitionsByUserID(int id);
}
