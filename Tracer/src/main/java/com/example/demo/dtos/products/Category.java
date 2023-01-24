package com.example.demo.dtos.products;

public class Category {
	private int id = -1;
	private int parent_id = -1;
	private String image_url = null;
	private int catalog_position = -1;
	private String size = null;
	private String title = null;
	
	public Category() {}

	public Category(
		int id,
		int parent_id,
		String image_url,
		int catalog_position,
		String size,
		String title
	) {
		this.id = id;
		this.parent_id = parent_id;
		this.image_url = image_url;
		this.catalog_position = catalog_position;
		this.size = size;
		this.title = title;	
	}
	
	public int getId() { return this.id; }
	public int getParent_id() { return this.parent_id; }
	public String getImage_url() { return this.image_url; }
	public int getCatalog_position() { return this.catalog_position; }
	public String getSize() { return this.size; }
	public String getTitle() { return this.title; }
	
	public void setId(int id) { this.id = id; }
	public void setParent_id(int parent_id) { this.parent_id = parent_id; }
	public void setImage_url(String image_url) { this.image_url = image_url; }
	public void setCatalog_position(int catalog_position) { this.catalog_position = catalog_position; }
	public void setSize(String size) { this.size = size; }
	public void setTitle(String title) { this.title = title; }
	
	public String toString() {
		return String.valueOf(this.id) +" "+
			   String.valueOf(this.parent_id) +" "+
			   image_url +" "+
			   String.valueOf(catalog_position) +" "+
			   size +" "+
			   title;
	}
}
