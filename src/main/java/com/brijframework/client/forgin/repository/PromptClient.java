package com.brijframework.client.forgin.repository;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.client.forgin.config.FeignClientConfig;
import com.brijframework.client.forgin.model.PromptLibarary;

@FeignClient(name= "UNLIMITS-CONTENT" ,configuration = FeignClientConfig.class, url = "http://${server.gateway.host}:${server.gateway.port}/content")
public interface PromptClient {

	@GetMapping(value = "/api/global/prompt/libarary")
	public Response<List<PromptLibarary>> getPrompts();

	@GetMapping(value = "/api/global/prompt/libarary")
	public Response<List<PromptLibarary>> getPromptsByYear(@RequestParam(value="year") Integer year);

	@GetMapping(value = "/api/global/prompt/libarary")
	public Response<List<PromptLibarary>> getPromptsBySubCategory(@RequestParam(value="subCategoryId") Long subCategoryId);
	
}