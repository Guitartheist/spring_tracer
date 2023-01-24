package com.example.demo.dtos.products;

import org.json.simple.JSONObject;

public class ProductVariantData  {
	private int id;
	private int product_id;
    private String name;
    private String size;
    private String color;
    private String color_code;
    private String color_code2;
    private String image;
    private String price;
    private boolean in_stock;
    private JSONObject availability_regions;
    private AvailabilityStatus[] availability_status;

    public ProductVariantData() {
		id = -1;
		product_id = -1;
		name = null;
		size = null;
		color = null;
		color_code = null;
		color_code2 = null;
		image = null;
		price = null;
		in_stock = false;
		availability_regions = null;
		availability_status = null;
    }

    public ProductVariantData(
		int id,
		int product_id,
	    String name,
	    String size,
	    String color,
	    String color_code,
	    String color_code2,
	    String image,
	    String price,
	    boolean in_stock,
	    JSONObject availability_regions,
	    AvailabilityStatus[] availability_status
    ) {
		this.id = id;
		this.product_id = product_id;
		this.name = name;
		this.size = size;
		this.color = color;
		this.color_code = color_code;
		this.color_code2 = color_code2;
		this.image = image;
		this.price = price;
		this.in_stock = in_stock;
		this.availability_regions = availability_regions ;
		this.availability_status = availability_status;
    }
    
	public int getId() { return this.id; }
	public int getProduct_id() { return this.product_id; }
	public String getName() { return this.name; }
	public String getSize() { return this.size; }
	public String getColor() { return this.color; }
	public String getColor_code() { return this.color_code; }
    public String getColor_code2() { return this.color_code2; }
    public String getImage() { return this.image; }
    public String getPrice() { return this.price; }
    public boolean getIn_stock() { return this.in_stock; }
    public JSONObject getAvailability_regions() { return this.availability_regions; }
    public AvailabilityStatus[] getAvailability_status() { return this.availability_status; }
    
	public void setId(int id) { this.id = id; }
	public void setProduct_id(int product_id) { this.product_id = product_id; }
	public void setName(String name) { this.name = name; }
	public void setSize(String size) { this.size = size; }
	public void setColor(String color) { this.color = color; }
	public void setColor_code(String color_code) { this.color_code = color_code; }
    public void setColor_code2(String color_code2) { this.color_code2 = color_code2; }
    public void setImage(String image) { this.image = image; }
    public void setPrice(String price) { this.price = price; }
    public void setIn_stock(boolean in_stock) { this.in_stock = in_stock; }
    public void setAvailability_regions(JSONObject availability_regions) { this.availability_regions = availability_regions; }
    public void setAvailability_status(AvailabilityStatus[] availability_status) { this.availability_status = availability_status; }
}

final class AvailabilityStatus {
	private String region;
	private String in_stock;

	public AvailabilityStatus() {
		region = null;
		in_stock = null;
	}

	public AvailabilityStatus(String region, String in_stock) {
		this.region = region;
		this.in_stock = in_stock;
	}

	public String getRegion() { return this.region; }
	public String getIn_stock() { return this.in_stock; }
	
	public void setRegion(String region) { this.region = region; }
	public void setIn_stock(String in_stock) { this.in_stock = in_stock; }
}
