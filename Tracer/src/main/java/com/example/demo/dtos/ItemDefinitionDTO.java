package com.example.demo.dtos;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.ItemDefinition;
import com.example.demo.services.UserService;

public class ItemDefinitionDTO {
	private int id;
	private int creator_id;
	private String name;
	private float minimum;
	private float maximum;
	
	public ItemDefinition getItemDefinition(UserService userService) {
		ItemDefinition item = new ItemDefinition();
		item.setId(id);
		if (userService.findUserById(creator_id) != null) {
			item.setCreator(userService.findUserById(creator_id));
		}
		else {
			return null;
		}
		item.setName(name);
		item.setMinimum(minimum);
		item.setMaximum(maximum);
		return item;
	}
	
	public void setItemDefinition(ItemDefinition item) {
		id = item.getId();
		creator_id = item.getCreator().getId();
		name = item.getName();
		minimum = item.getMinimum();
		maximum = item.getMaximum();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCreator_id() {
		return creator_id;
	}
	
	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public float getMinimum() {
		return minimum;
	}
	
	public void setMinimum(float minimum) {
		this.minimum = minimum;
	}
	
	public float getMaximum() {
		return maximum;
	}
	
	public void setMaximum(float maximum) {
		this.maximum = maximum;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(creator_id, id, maximum, minimum, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDefinitionDTO other = (ItemDefinitionDTO) obj;
		return creator_id == other.creator_id && id == other.id
				&& Float.floatToIntBits(maximum) == Float.floatToIntBits(other.maximum)
				&& Float.floatToIntBits(minimum) == Float.floatToIntBits(other.minimum)
				&& Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		return "ItemDefinitionDTO [id=" + id + ", creator_id=" + creator_id + ", name=" + name + ", minimum=" + minimum
				+ ", maximum=" + maximum + "]";
	}
}
