package com.example.demo.dtos;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.DailyItemEntry;
import com.example.demo.services.ItemDefinitionService;
import com.example.demo.services.UserService;

public class DailyItemEntryDTO {
	private int id;
	private int user_id;
	private int item_id;
	private Date date;
	private float value;
	private String notes;
	
	public DailyItemEntry getDailyItemEntry(UserService userService, ItemDefinitionService itemDefinitionService) {
		DailyItemEntry entry = new DailyItemEntry();
		entry.setId(id);
		if (userService.findUserById(user_id) != null) {
			entry.setUser(userService.findUserById(user_id));
		}
		else {
			return null;
		}
		if (itemDefinitionService.findById(item_id).isPresent()) {
			entry.setItem(itemDefinitionService.findById(item_id).get());
		}
		else {
			return null;
		}
		entry.setDate(date);
		entry.setValue(value);
		entry.setNotes(notes);
		return entry;
	}
	
	public void setDailyItemEntry(DailyItemEntry entry) {
		id = entry.getId();
		user_id = entry.getUser().getId();
		item_id = entry.getItem().getId();
		date = entry.getDate();
		value = entry.getValue();
		notes = entry.getNotes();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, id, item_id, notes, user_id, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DailyItemEntryDTO other = (DailyItemEntryDTO) obj;
		return Objects.equals(date, other.date) && id == other.id && item_id == other.item_id
				&& Objects.equals(notes, other.notes) && user_id == other.user_id
				&& Float.floatToIntBits(value) == Float.floatToIntBits(other.value);
	}

	@Override
	public String toString() {
		return "DailyItemEntryDTO [id=" + id + ", user_id=" + user_id + ", item_id=" + item_id + ", date=" + date
				+ ", value=" + value + ", notes=" + notes + "]";
	}
}
