package com.example.dtos;

import java.util.UUID;

public class ImageCreateRequestBody {

	private int fn_index;
	private Object[] data;
	private String sessionHash;

	public ImageCreateRequestBody(int fn_index, Object[] data) {
		this.fn_index = fn_index;
		this.data = data;
		this.sessionHash = generateSessionHash();
	}
	
	public ImageCreateRequestBody(int fn_index, Object[] data, String sessionHash) {
		this.fn_index = fn_index;
		this.data = data;
		this.sessionHash = sessionHash;
	}
	
	public ImageCreateRequestBody(int fn_index) {
		this.fn_index = fn_index;
		this.data = new Object[0];
		this.sessionHash = generateSessionHash();
	}
	
	public ImageCreateRequestBody(int fn_index, String sessionHash) {
		this.fn_index = fn_index;
		this.data = new Object[0];
		this.sessionHash = sessionHash;
	}

	// getters
	public int getFn_index() { return this.fn_index; }
	public Object[] getData() { return this.data; }
	public String getSessionHash() { return this.sessionHash; }

	// setters
	public void setFn_index(int fn_index) { this.fn_index = fn_index; }
	public void setData(Object[] data) { this.data = data; }
    public void setSessionHash(String sessionHash) { this.sessionHash = sessionHash; }

    public String toString() {
    	return "fn_index: "+this.fn_index+"\n"
    		  +"data: "+ this.data.toString()+"\n"
    		  +"sessionHash: "+this.sessionHash;
    }
	
	// quick and dirty 'session_hash'
    private static String generateSessionHash() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "").substring(0,11);
    }
}
