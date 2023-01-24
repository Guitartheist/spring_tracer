package com.example.demo.dtos;

public class ImageCreateRequest {

	private String prompt;
	private int width;
	private int height;
	private int batch_size;
	
	public ImageCreateRequest() {
		this.prompt = null;
		this.width= 0;
		this.height = 0;
		this.batch_size = 1;
	}
	
	public ImageCreateRequest(String prompt, int width, int height) {
		this.prompt = prompt;
		this.width = width;
		this.height = height;
		this.batch_size = 1;
	}

	public ImageCreateRequest(String prompt, int width, int height, int batch_size) {
		this.prompt = prompt;
		this.width = width;
		this.height = height;
		this.batch_size = batch_size;
	}
	
	public String getPrompt() { return this.prompt; }
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public int getBatch_size() { return this.batch_size; }

	public void setPrompt(String prompt) { this.prompt = prompt; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public void setBatch_size(int batch_size) { this.batch_size = batch_size; }
}
