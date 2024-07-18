package com.brijframework.client.forgin.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.brijframework.client.forgin.model.UIResource;

@FeignClient(name= "UNLIMITS-CONTENT" , url = "http://localhost:3333")
public interface ResourceRepository {

	@PostMapping(value = "/resource/{type}/{name}")
	public UIResource add(@PathVariable String type, @PathVariable String name,@RequestBody String content);
	
}