package com.example.demo.dtos.products;

import org.json.simple.JSONObject;

public class ProductProductData {
	private int id;
    private int  main_category_id;
    private String type;
    private String type_name;
    private String title;
    private String brand;
    private String model;
    private String image;
    private int variant_count;
    private String currency;
    private String[] options;
    private JSONObject dimensions;
    private boolean is_discontinued;
    private String avg_fulfillment_time;
    private Techniques[] techniques;
    private Files[] files;
    private String description;

    public ProductProductData() {
    	this.id = -1;
    	this.main_category_id = -1;
        this.type = null;
        this.type_name = null;
        this.title = null;
        this.brand = null;
        this.model = null;
        this.image = null;
        this.variant_count = -1;
        this.currency = null;
        this.options = null;
        this.dimensions = null;
        this.is_discontinued = true;
        this.avg_fulfillment_time = null;
        this.techniques = null;
        this.files = null;
        this.description = null;
    }
        
    public ProductProductData(
		int id,
		int main_category_id,
		String type,
		String type_name,
		String title,
		String brand,
		String model,
		String image,
		int variant_count,
		String currency,
		String[] options,
		JSONObject dimensions,
		boolean is_discontinued,
		String avg_fulfillment_time,
		Techniques[] techniques,
		Files[] files,
		String description
	) {
    	this.id = -1;
    	this.main_category_id = -1;
        this.type = null;
        this.type_name = null;
        this.title = null;
        this.brand = null;
        this.model = null;
        this.image = null;
        this.variant_count = -1;
        this.currency = null;
        this.options = null;
        this.dimensions = null;
        this.is_discontinued = true;
        this.avg_fulfillment_time = null;
        this.techniques = null;
        this.files = null;
        this.description = null;
    }
	public int getId() { return this.id; }
	public int  getMain_category_id() { return this.main_category_id; }
	public String getType() { return this.type; }
	public String getType_name() { return this.type_name; }
	public String getTitle() { return this.title; }
	public String getBrand() { return this.brand; }
	public String getModel() { return this.model; }
	public String getImage() { return this.image; }
	public int getVariant_count() { return this.variant_count; }
	public String getCurrency() { return this.currency; }
	public String[] getOptions() { return this.options; }
	public JSONObject getDimensions() { return this.dimensions; }
	public boolean getIs_discontinued() { return this.is_discontinued; }
	public String getAvg_fulfillment_time() { return this.avg_fulfillment_time; }
	public Techniques[] getTechniques() { return this.techniques; }
	public Files[] getFiles() { return this.files; }
	public String getDescription() { return this.description; }
    
	public void setId(int id) { this.id = id; }
	public void setMain_category_id(int main_category_id) { this.main_category_id = main_category_id; }
	public void setType(String type) { this.type = type; }
	public void setType_name(String type_name) { this.type_name = type_name; }
	public void setTitle(String title) { this.title = title; }
	public void setBrand(String brand) { this.brand = brand; }
	public void setModel(String model) { this.model = model; }
	public void setImage(String image) { this.image = image; }
	public void setVariant_count(int variant_count) { this.variant_count = variant_count; }
	public void setCurrency(String currency) { this.currency = currency; }
	public void setOptions(String[] options) { this.options = options; }
	public void setDimensions(JSONObject dimensions) { this.dimensions = dimensions; }
	public void setIs_discontinued(boolean is_discontinued) { this.is_discontinued = is_discontinued; }
	public void setAvg_fulfillment_time(String avg_fulfillment_time) { this.avg_fulfillment_time = avg_fulfillment_time; }
	public void setTechniques(Techniques[] techniques) { this.techniques = techniques; }
	public void setFiles(Files[] files) { this.files = files; }
	public void setDescription(String description) { this.description = description; }
}

final class Techniques {
	private String key;
	private String display_name;
	private boolean is_default;

	public Techniques() {
		this.key = null;
		this.display_name = null;
		this.is_default = false;			
	}

	public Techniques(String key, String display_name, boolean is_default) {
		this.key = key;
		this.display_name = display_name;
		this.is_default = is_default;
	}
	
	public String getKey() { return this.key; }
	public String getDisplay_name() { return this.display_name; }
	public boolean getIs_default() { return this.is_default; }

	public void setKey(String key) { this.key = key; }
	public void setDisplay_name(String display_name) { this.display_name = display_name; }
	public void setIs_default(boolean is_default) { this.is_default = is_default; }
}

final class Files {
	private String id;
	private String type;
	private String title;
	private String additional_price;
	
	public Files() {
		this.id = null;
		this.type = null;
		this.title = null;
		this.additional_price = null;
	}
	
	public Files(String id, String type, String title, String additional_price) {		
		this.id = id;
		this.type = type;
		this.title = title;
		this.additional_price = additional_price;
	}

	public String getId() { return this.id; }
	public String getType() { return this.type; }
	public String getTitle() { return this.title; }
	public String getAdditional_price () { return this.additional_price; }

	public void setId(String id) { this.id = id; }
	public void setType(String type) { this.type = type; }
	public void setTitle(String title) { this.title = title; }
	public void setAdditional_price(String additional_price) { this.additional_price = additional_price; }
}
