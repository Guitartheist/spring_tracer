package com.example.demo.dtos.products;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductData {
	private ProductProductData product = null;
	private ProductVariantData[] variants = null;
	
	public ProductData() {}
	
	public ProductData(
			@JsonProperty("product")
			ProductProductData product,
			@JsonProperty("variants")
			ProductVariantData[] variants
	) {
		this.product = product;
		this.variants = variants;
	}

	public ProductProductData getProduct( ) { return this.product; }
	public ProductVariantData[] getVariants( ) { return this.variants; }
	
	public void setProduct(ProductProductData product) {
		this.product = product;
	}
	public void setVariants(ProductVariantData[] variants) {
		this.variants = variants;
	}
}
