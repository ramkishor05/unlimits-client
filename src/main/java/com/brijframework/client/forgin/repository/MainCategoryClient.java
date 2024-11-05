package com.brijframework.client.forgin.repository;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.client.forgin.config.FeignClientConfig;
import com.brijframework.client.forgin.model.MainCategoryModel;

@FeignClient(name = "UNLIMITS-CONTENT", configuration = FeignClientConfig.class, url = "http://${server.gateway.host}:${server.gateway.port}/content")
public interface MainCategoryClient {

	@GetMapping(value = "/api/global/main/category/findAllById/{ids}")
	public Response<List<MainCategoryModel>> getMainCategoryModelList(@PathVariable List<Long> ids);
	
	@GetMapping(value = "/api/global/main/category")
	public Response<List<MainCategoryModel>> getMainCategoryModelList();
	
}