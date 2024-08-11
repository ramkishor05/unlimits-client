package com.brijframework.client.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;

import com.brijframework.client.device.model.UIDeviceCoachGroup;
import com.brijframework.client.device.service.DeviceCoachService;
import com.brijframework.client.entities.EOCoachGroup;


@RestController
@RequestMapping("/api/device/coach/libarary")
public class DeviceCoachLibararyController implements QueryController<UIDeviceCoachGroup, EOCoachGroup, Long>{

	@Autowired
	private DeviceCoachService deviceCoachLibararyService;

	@Override
	public DeviceCoachService getService() {
		return deviceCoachLibararyService;
	}
}
