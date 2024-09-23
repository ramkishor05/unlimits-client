package com.brijframework.client.forgin.repository;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.client.forgin.config.FeignClientConfig;
import com.brijframework.client.forgin.model.ClientOnBoardingQuestion;

@FeignClient(name= "UNLIMITS-AUTH" ,configuration = FeignClientConfig.class, url = "http://${server.gateway.host}:${server.gateway.port}/auth")
public interface OnboardingClient {

	@GetMapping(value = "/api/device/onboarding/question")
	public Response<List<ClientOnBoardingQuestion>> getOnboardings();
	
}