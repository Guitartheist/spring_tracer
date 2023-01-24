package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageCreateResponse {
	
	@JsonProperty("images")
	private String[] images;

	@JsonProperty("parameters")
	private Object parameters;
	
	@JsonProperty("info")
	private String info;
	
	public ImageCreateResponse() {
		this.images = null;
		this.parameters = null;
		this.info = "";
	}
	
	public ImageCreateResponse(String[]  images, Object parameters, String info) {
		this.images = images;
		this.parameters = parameters;
		this.info = info;	
	}
	
	public String[] getImages() { return this.images; }
	public Object getParameters() { return this.parameters; }
	public String getInfo() { return this.info; }
	
	public void setImages(String[]  images) { this.images = images; }
	public void setParameters(Object parameters) { this.parameters = parameters; }
	public void setInfo(String info) { this.info = info; }	
}
