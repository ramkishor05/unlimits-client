package com.brijframework.client.forgin.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brijframework.client.forgin.config.FeignClientConfig;
import com.brijframework.client.forgin.model.PromptLibararyResponse;

@FeignClient(name= "UNLIMITS-CONTENT" ,configuration = FeignClientConfig.class, url = "http://localhost:3333")
public interface PromptClient {

	@GetMapping(value = "/api/global/prompt/libarary")
	public PromptLibararyResponse getPrompts();

	@GetMapping(value = "/api/global/prompt/libarary")
	public PromptLibararyResponse getPromptsByYear(@RequestParam(value="year") Integer year);

	@GetMapping(value = "/api/global/prompt/libarary")
	public PromptLibararyResponse getPromptsBySubCategory(@RequestParam(value="subCategoryId") Long subCategoryId);
	
}