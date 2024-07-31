/**
 * 
 */
package com.brijframework.client.unlimits.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.unlimits.device.service.DeviceClientVisualizeService;
import com.brijframework.client.unlimits.model.UIClientVisualize;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/client/visualize"})
@CrossOrigin("*")
public class DeviceClientVisualizeController {
	
	@Autowired
	private DeviceClientVisualizeService clientVisualizeService;

	@GetMapping("/year/{year}/type/{type}/unlimit/{unlimitId}")
	public UIClientVisualize  getVisualize(@RequestParam Integer year, @RequestParam UnlimitsType type,@RequestParam Long unlimitId) {
		return clientVisualizeService.getVisualize(year, type, unlimitId);
	}
}
