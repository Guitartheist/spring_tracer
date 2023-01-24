package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dtos.products.ResponseObject;
import com.example.demo.dtos.products.ProductData;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	RestTemplate restTemplate;

	//wall art
	final private int ENHANCED_MATTE_PAPER_POSTER_IN = 1;			// posters
	final private int PREMIUM_LUSTER_PHOTO_PAPER_POSTER_IN = 171;
	final private int CANVAS_IN = 3;								// canvas
	
	@Autowired
	public ProductController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	// Creates an order, need to rewrite as POST request
	@GetMapping(value="/order")
	public String orderProduct() {
		String imgUrl = "https://via.placeholder.com/1800x2700/09f/FFA500.png";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer P1P5s3FpRJrMZRSC7flDyBwEbTWdS600fzcU3WVh");
		
		JsonObject data = Json.createObjectBuilder()
			.add("recipient", Json.createObjectBuilder()
				.add("name", "Jose Sanchez")
				.add("address1", "12 address avenue, Bankstown")
				.add("city", "Sydney")
				.add("state_code", "NSW")
				.add("country_code", "AU")
				.add("zip", "2200"))
			.add("items", Json.createArrayBuilder()
				.add(Json.createObjectBuilder()
					.add("variant_id", 3876)
					.add("quantity", 1)
					.add("files", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("url", imgUrl)))))
			.build();
		
		HttpEntity<String> request = new HttpEntity<>(data.toString(), headers);

		String url = "https://api.printful.com/orders";
		String orderCreateResponse = restTemplate.postForObject(url, request, String.class);
		
		System.out.println(orderCreateResponse);
		return orderCreateResponse;
	}
	
	// Get all wall art products, Note: removed framed wall-art
	@GetMapping(value="/data/wall-art")
	public List<ProductData> getProductData_WallArt() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer P1P5s3FpRJrMZRSC7flDyBwEbTWdS600fzcU3WVh");
		HttpEntity<Void> request = new HttpEntity<>(headers);

		List<ProductData> dataArray = new ArrayList<>();

		String url = "https://api.printful.com/products/"+ENHANCED_MATTE_PAPER_POSTER_IN;
		ResponseEntity<ResponseObject> response = restTemplate.exchange(url, HttpMethod.GET, request, ResponseObject.class);
		dataArray.add(response.getBody().getResult());

		url = "https://api.printful.com/products/"+PREMIUM_LUSTER_PHOTO_PAPER_POSTER_IN;
		response = restTemplate.exchange(url, HttpMethod.GET, request, ResponseObject.class);
		dataArray.add(response.getBody().getResult());

		/*url = "https://api.printful.com/products/"+ENHANCED_MATTE_PAPER_FRAMED_POSTER_IN;
		response = restTemplate.exchange(url, HttpMethod.GET, request, ResponseObject.class);
		dataArray.add(response.getBody().getResult());*/

		/*url = "https://api.printful.com/products/"+PREMIUM_LUSTER_PHOTO_FRAMED_POSTER_IN;
		response = restTemplate.exchange(url, HttpMethod.GET, request, ResponseObject.class);
		dataArray.add(response.getBody().getResult());*/

		url = "https://api.printful.com/products/"+CANVAS_IN;
		response = restTemplate.exchange(url, HttpMethod.GET, request, ResponseObject.class);
		dataArray.add(response.getBody().getResult());

		/*url = "https://api.printful.com/products/"+FRAMED_CANVAS_IN;
		response = restTemplate.exchange(url, HttpMethod.GET, request, ResponseObject.class);
		dataArray.add(response.getBody().getResult());*/

		/*url = "https://api.printful.com/products/"+THIN_CANVAS_IN;
		response = restTemplate.exchange(url, HttpMethod.GET, request, ResponseObject.class);
		dataArray.add(response.getBody().getResult());*/

		return dataArray;
	}
	
	// Get specific product data based on product id
	@GetMapping(value="/data/{productId}")
	public ProductData getProductData(@PathVariable("productId") String productId) {
		String url = "https://api.printful.com/products/"+productId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer P1P5s3FpRJrMZRSC7flDyBwEbTWdS600fzcU3WVh");
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<ResponseObject> response = restTemplate.exchange(url, HttpMethod.GET, request, ResponseObject.class);		
		
		return response.getBody().getResult();
	}
}
