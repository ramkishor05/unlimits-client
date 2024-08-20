package com.brijframework.client.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;

import com.brijframework.client.device.model.UIUnlimitsCoachConversion;
import com.brijframework.client.device.service.DeviceUnlimitCoachConversionService;
import com.brijframework.client.entities.EOUnlimitsCoachConversion;


@RestController
@RequestMapping("/api/device/unlimits/coach/conversion")
public class DeviceUnlimitsCoachConversionController implements CrudController<UIUnlimitsCoachConversion, EOUnlimitsCoachConversion, Long>{

	@Autowired
	private DeviceUnlimitCoachConversionService deviceCoachService;

	@Override
	public DeviceUnlimitCoachConversionService getService() {
		return deviceCoachService;
	}
}
