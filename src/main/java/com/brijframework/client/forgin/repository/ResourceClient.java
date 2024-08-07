package com.brijframework.client.forgin.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.brijframework.client.forgin.config.FeignClientConfig;
import com.brijframework.client.forgin.model.ResourceFile;

@FeignClient(name = "UNLIMITS-CONTENT", configuration = FeignClientConfig.class, url = "http://51.79.159.7:3333")
public interface ResourceClient {

	@PostMapping(value = "/resource/{type}/{name}")
	public ResourceFile add(@PathVariable String type, @PathVariable String name, @RequestBody String content);

}