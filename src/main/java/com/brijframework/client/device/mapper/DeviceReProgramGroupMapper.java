package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceReProgramGroup;
import com.brijframework.client.entities.EOReProgramGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceReProgramGroupMapper extends GenericMapper<EOReProgramGroup, UIDeviceReProgramGroup>{

	@Override
	@Mapping(target = "reprograms", ignore = true)
	EOReProgramGroup mapToDAO(UIDeviceReProgramGroup d);
}
