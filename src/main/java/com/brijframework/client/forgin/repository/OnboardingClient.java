package com.brijframework.client.forgin.repository;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.brijframework.client.forgin.model.ClientOnBoardingQuestion;

@FeignClient(name= "UNLIMITS-AUTH" , url = "http://localhost:2222")
public interface OnboardingClient {

	@GetMapping(value = "/api/device/onboarding/question")
	public List<ClientOnBoardingQuestion> getOnboardings();
	
}