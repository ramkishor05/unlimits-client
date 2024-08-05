/**
 * 
 */
package com.brijframework.client.unlimits.device.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.unlimits.rest.crud.controller.CQRSController;

import com.brijframework.client.unlimits.device.service.DeviceClientVisualizeService;
import com.brijframework.client.unlimits.model.UIClientUnlimits;
import com.brijframework.client.unlimits.model.UIClientVisualizeRequest;
import com.brijframework.client.unlimits.model.UIClientVisualizeResponse;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({ "/api/device/client/visualize" })
@CrossOrigin("*")
public class DeviceClientVisualizeController {

	@Autowired
	private DeviceClientVisualizeService clientVisualizeService;

	@PostMapping
	public UIClientVisualizeResponse  add(@RequestBody  UIClientVisualizeRequest clientVisualizeRequest) {
		return clientVisualizeService.add(clientVisualizeRequest);
	}

	@GetMapping
	public List<UIClientUnlimits> findAll(@RequestHeader(required = false) MultiValueMap<String, String> headers,
			WebRequest webRequest) {
		return clientVisualizeService.findAll(headers, CQRSController.getfilters(webRequest));
	}

}
