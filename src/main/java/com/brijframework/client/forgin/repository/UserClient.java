package com.brijframework.client.forgin.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.brijframework.client.constants.Constants;
import com.brijframework.client.forgin.model.UserAccountModel;

@FeignClient(name= "UNLIMITS-AUTH" , url = "http://${server.gateway.host}:${server.gateway.port}/auth")
public interface UserClient {

	@GetMapping(value = "/api/device/authentication")
	public UserAccountModel findByToken(@RequestHeader(Constants.AUTHORIZATION) String token) ;;
}
