package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.DailyItemEntry;

@Repository
public interface DailyItemEntryRepo extends CrudRepository<DailyItemEntry, Integer> {
	@Query("from DailyItemEntry d where d.user.id = :id")
	List<DailyItemEntry> findDailyItemEntriesByUserID(int id);
}
