package com.brijframework.client.forgin.repository;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brijframework.client.forgin.model.PromptLibarary;

@FeignClient(name= "UNLIMITS-CONTENT" , url = "http://localhost:3333")
public interface PromptClient {

	@GetMapping(value = "/api/global/prompt/libarary")
	public List<PromptLibarary> getPrompts();

	@GetMapping(value = "/api/global/prompt/libarary")
	public PromptLibarary getPromptsByYear(@RequestParam(value="year") Integer year);

	@GetMapping(value = "/api/global/prompt/libarary")
	public PromptLibarary getPromptsBySubCategory(@RequestParam(value="subCategoryId") Long subCategoryId);
	
}