package com.example.demo.dtos.products;

public class ResponseObject {
	private int code = -1;
	private ProductData result = null;
	private Object[] extras = null;
	
	public ResponseObject() {}
	
	public ResponseObject(
		int code,
		ProductData result,
		Object[] extras
	) {
		this.code = code;
		this.result = result;
		this.extras = extras;
	}
	
	public int getCode() { return this.code; }
	public ProductData getResult() { return this.result; }
	public Object[] getExtras() { return this.extras; }

	public void setCode(int code) { this.code = code; }
	public void setResult(ProductData result) { this.result = result; }
	public void setExtras(Object[] extras) { this.extras = extras; }
}
