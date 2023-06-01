package com.example.demo.models;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table( uniqueConstraints = { @UniqueConstraint( columnNames = { "user", "item", "date" } ) } )
public class DailyItemEntry {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user", referencedColumnName = "id")
	private AppUser user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item", referencedColumnName = "id")
	private ItemDefinition item;
	@Basic
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column(nullable = true)
	private float value;
	@Column(nullable = true)
	private String notes;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public AppUser getUser() {
		return user;
	}
	
	public void setUser(AppUser user) {
		this.user = user;
	}
	
	public ItemDefinition getItem() {
		return item;
	}
	
	public void setItem(ItemDefinition item) {
		this.item = item;
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
		return Objects.hash(date, id, item, notes, user, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DailyItemEntry other = (DailyItemEntry) obj;
		return Objects.equals(date, other.date) && id == other.id && Objects.equals(item, other.item)
				&& Objects.equals(notes, other.notes) && Objects.equals(user, other.user)
				&& Float.floatToIntBits(value) == Float.floatToIntBits(other.value);
	}
	
	@Override
	public String toString() {
		return "DailyItemEntry [id=" + id + ", user=" + user + ", item=" + item + ", date=" + date + ", value=" + value
				+ ", notes=" + notes + "]";
	}
}
