package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.DailyItemEntry;
import com.example.demo.repos.DailyItemEntryRepo;

@Service
public class DailyItemEntryService {
	private DailyItemEntryRepo dailyItemEntryRepo;
	
	@Autowired
	public DailyItemEntryService(DailyItemEntryRepo dailyItemEntryRepo) {
		this.dailyItemEntryRepo = dailyItemEntryRepo;
	}
	
	public Optional<DailyItemEntry> findById(int id) {
		return dailyItemEntryRepo.findById(id);
	}
	
	public DailyItemEntry create(DailyItemEntry entry) {
		if (entry.getItem().getMinimum() > 0.0 
				&& entry.getItem().getMaximum() > 0.0) {
			if (entry.getValue() < entry.getItem().getMinimum() 
				|| entry.getValue() > entry.getItem().getMaximum()) {
				return null;
			}
		}
		return dailyItemEntryRepo.save(entry);
	}
	
	public List<DailyItemEntry> findByUserId(int id) {
		return dailyItemEntryRepo.findDailyItemEntriesByUserID(id);
	}
	
	public DailyItemEntry update(DailyItemEntry entry) {
		if (entry.getItem().getMinimum() > 0.0 
				&& entry.getItem().getMaximum() > 0.0) {
			if (entry.getValue() < entry.getItem().getMinimum() 
				|| entry.getValue() > entry.getItem().getMaximum()) {
				return null;
			}
		}
		return dailyItemEntryRepo.save(entry);
	}
	
	public void delete(DailyItemEntry entry) {
		dailyItemEntryRepo.delete(entry);
	}
}
