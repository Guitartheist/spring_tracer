package com.example.demo.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemDefinition {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = true)
	private AppUser creator;
	@Column(nullable = false)
	private String name;
	@Column(nullable = true)
	private float minimum;
	@Column(nullable = true)
	private float maximum;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public AppUser getCreator() {
		return creator;
	}
	
	public void setCreator(AppUser creator) {
		this.creator = creator;
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
		return Objects.hash(creator, id, maximum, minimum, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDefinition other = (ItemDefinition) obj;
		return Objects.equals(creator, other.creator) && id == other.id
				&& Float.floatToIntBits(maximum) == Float.floatToIntBits(other.maximum)
				&& Float.floatToIntBits(minimum) == Float.floatToIntBits(other.minimum)
				&& Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		return "ItemDefinition [id=" + id + ", creator=" + creator + ", name=" + name + ", minimum=" + minimum
				+ ", maximum=" + maximum + "]";
	}
}
