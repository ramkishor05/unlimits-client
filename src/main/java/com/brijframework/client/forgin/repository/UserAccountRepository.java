package com.brijframework.client.forgin.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.brijframework.client.constants.Constants;
import com.brijframework.client.forgin.model.UIUserAccount;

@FeignClient(name= "UNLIMITS-AUTH" , url = "http://${server.eureka.host}:${server.eureka.port}/auth")
public interface UserAccountRepository {

	@GetMapping(value = "/api/device/authentication")
	public UIUserAccount findByToken(@RequestHeader(Constants.AUTHORIZATION) String token) ;;
}
