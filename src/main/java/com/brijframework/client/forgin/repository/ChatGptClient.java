package com.brijframework.client.forgin.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.brijframework.client.forgin.config.FeignClientConfig;

@FeignClient(name= "UNLIMITS-INTEGRATION" ,configuration = FeignClientConfig.class, url = "http://localhost:4444")
public interface ChatGptClient {

	@PostMapping(value = "/api/chatgpt/media/text", consumes = {MediaType.TEXT_PLAIN_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
	public String getTextResult(@RequestBody String prompt);
	
}