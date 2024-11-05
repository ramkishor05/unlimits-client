package com.brijframework.client.forgin.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.brijframework.client.forgin.config.FeignClientConfig;
import com.brijframework.client.forgin.model.ResourceFileModel;

@FeignClient(name = "UNLIMITS-CONTENT", configuration = FeignClientConfig.class, url = "http://${server.gateway.host}:${server.gateway.port}/content")
public interface ResourceClient {

	@PostMapping(value = "/resource/{type}/{name}")
	public ResourceFileModel add(@PathVariable String type, @PathVariable String name, @RequestBody String content);

}