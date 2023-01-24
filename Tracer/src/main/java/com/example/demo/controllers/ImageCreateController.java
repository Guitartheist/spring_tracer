package com.example.demo.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dtos.ImageCreateRequest;
import com.example.demo.dtos.ImageCreateResponse;
import com.example.demo.models.Images;

@RestController
@RequestMapping("/image")
public class ImageCreateController {

	RestTemplate restTemplate;
	String artLayerIP = "108.61.142.117";
	
	@Autowired
	public ImageCreateController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping(value = "/healthCheck")
	@ResponseBody
	public String healthCheck()
	{
		return "healthy";
	}
	
	@PostMapping(value="/create")
	public ResponseEntity<Images> createImage(
		@RequestBody ImageCreateRequest reqBody
	) {
		String baseURL = "http://"+artLayerIP;
		String imageCreateURL   = baseURL+":7860/sdapi/v1/txt2img";
		
		HttpHeaders headers = new HttpHeaders();
		MediaType headersList[] = new MediaType[] {
				MediaType.APPLICATION_JSON,
		};	
		headers.setAccept(Arrays.asList(headersList));
		ImageCreateRequest body = new ImageCreateRequest(
			reqBody.getPrompt(),
			reqBody.getWidth(),
			reqBody.getHeight(),
			4
		);
		HttpEntity<ImageCreateRequest> request = new HttpEntity<>(body, headers);
		ImageCreateResponse response = restTemplate.postForObject(
			imageCreateURL,
			request,
			ImageCreateResponse.class
		);
		Images images = new Images(response.getImages());
		return ResponseEntity.ok().body(images);
	}
}
