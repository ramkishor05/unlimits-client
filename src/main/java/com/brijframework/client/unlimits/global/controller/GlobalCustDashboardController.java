package com.brijframework.client.unlimits.global.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.client.unlimits.global.service.GlobalCustDashboardService;
import com.brijframework.client.unlimits.model.UIGlobalDashboard;

import io.swagger.v3.oas.annotations.Hidden;

/**
 *  @author ram kishor
 */
@RestController
@RequestMapping(value = "/api/global/dashboard")
@CrossOrigin("*")
@Hidden
public class GlobalCustDashboardController {
	
	private GlobalCustDashboardService globalCustDashboardService;
	
	@GetMapping
	public UIGlobalDashboard getGlobalClientDashboard() {
		return globalCustDashboardService.getDashboard();
	}

}
