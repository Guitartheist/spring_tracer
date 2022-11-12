package com.example.demo.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dtos.ImageCreateRequestBody;

@RestController
@RequestMapping("/image")
public class ImageCreateController {

	//@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	public ImageCreateController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
		
	@GetMapping(
		value = "/create",
		produces = MediaType.IMAGE_PNG_VALUE
	)
	@ResponseBody
	public byte[] createImage(@RequestParam String prompt) {
		String baseURL = "http://45.76.3.128";
		String imageCreateURL   = baseURL+":7860/run/predict";
		String imageRetrieveURL = baseURL+":7860/file=";
		
		HttpHeaders headers = new HttpHeaders();       // create headers
		MediaType headersList[] = new MediaType[] {
				MediaType.APPLICATION_JSON,
		};	
		headers.setAccept(Arrays.asList(headersList));
		
		ImageCreateRequestBody data = new ImageCreateRequestBody(38); // create request body
		HttpEntity<ImageCreateRequestBody> request = new HttpEntity<>(data, headers);

System.out.println("Initial Call for prompt: "+prompt);
		restTemplate.postForObject(imageCreateURL, request, String.class); // Initial call, might not need

		data.setFn_index(50);
		// data for image creation
		Object[] dataArray = {prompt,"","None","None",20,"Euler a",false,false,1,1,7,-1,-1,0,0,0,false,512,512,false,0.7,0,0,"None",false,false,false,"","Seed","","Nothing","",true,false,false,null,"{\"prompt\": prompt, \"all_prompts\": [prompt], \"negative_prompt\": \"\", \"seed\": 2466942135, \"all_seeds\": [2466942135], \"subseed\": 2024935946, \"all_subseeds\": [2024935946], \"subseed_strength\": 0, \"width\": 512, \"height\": 512, \"sampler_index\": 0, \"sampler\": \"Euler a\", \"cfg_scale\": 7, \"steps\": 20, \"batch_size\": 1, \"restore_faces\": false, \"face_restoration_model\": null, \"sd_model_hash\": \"7460a6fa\", \"seed_resize_from_w\": 0, \"seed_resize_from_h\": 0, \"denoising_strength\": null, \"extra_generation_params\": {}, \"index_of_first_image\": 0, \"infotexts\": [prompt\\nSteps: 20, Sampler: Euler a, CFG scale: 7, Seed: 2466942135, Size: 512x512, Model hash: 7460a6fa\"], \"styles\": [\"None\", \"None\"], \"job_timestamp\": \"20221111213700\", \"clip_skip\": 1}","<p>"+prompt+"<br>\nSteps: 20, Sampler: Euler a, CFG scale: 7, Seed: 2466942135, Size: 512x512, Model hash: 7460a6fa</p><div class='performance'><p class='time'>Time taken: <wbr>1.28s</p><p class='vram'>Torch active/reserved: 3161/3690 MiB, <wbr>Sys VRAM: 6244/8188 MiB (76.26%)</p></div>"};
		data.setData(dataArray);

		request = new HttpEntity<>(data, headers);
System.out.println("Calling Image Create...");
		String result = restTemplate.postForObject(imageCreateURL, request, String.class); // Create Image Call
				
		int startIndex = result.indexOf("/tmp");
		int endIndex = result.indexOf("\",");
		String imageFilePath =result.substring(startIndex, endIndex); // get file name and relative path
System.out.println(imageFilePath);

System.out.println("Checking Progress...");
		data.setFn_index(37);
		data.setData(new Object[0]);
		request = new HttpEntity<>(data, headers); 
		result = restTemplate.postForObject(imageCreateURL, request, String.class); // Check Progress Call
System.out.println(result);
	
System.out.println("Retrieving Image...");
		String GetImageURL = imageRetrieveURL+imageFilePath;	
		byte[] image = restTemplate.getForObject(GetImageURL, byte[].class);
		return image;
	}
}

/*
1 prompt;
2 negativePrompt;
3 unKnown1;
3 samplingSteps;
4 samplingMethod;
5 unKnown2;
6 unKnown3;
7 batchCount;
8 batchSize;
9 cfgScale;
10 seed;
11 unKnown4;
12 unKnown5;
13 unKnown6;
14 unKnown7;
15 unKnown8;
16 width;
17 height;
18 unKnown9;
19 unKnown10;
20 unKnown11;
21 unKnown12;
22 script;
23 unKnown13;
24 unKnown14;
25 unKnown15;
26 unKnown16;
27 unKnown17;
28 unKnown18;
29 unKnown19;
30 unKnown20;
31 unKnown21;
32 unKnown22;
33 unKnown23;
34 unKnown24;
35 unKnown25;
36 unKnown26;		
 */
