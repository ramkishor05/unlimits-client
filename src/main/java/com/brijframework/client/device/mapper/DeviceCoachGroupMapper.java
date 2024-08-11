package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceCoachGroup;
import com.brijframework.client.entities.EOCoachGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceCoachGroupMapper extends GenericMapper<EOCoachGroup, UIDeviceCoachGroup>{

	@Override
	@Mapping(target = "coachs", ignore = true)
	EOCoachGroup mapToDAO(UIDeviceCoachGroup d) ;
}
