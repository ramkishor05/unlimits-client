package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceMindSetGroup;
import com.brijframework.client.entities.EOMindSetGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceMindSetGroupMapper extends GenericMapper<EOMindSetGroup, UIDeviceMindSetGroup>{

	@Override
	@Mapping(target = "mindSets", ignore = true)
	EOMindSetGroup mapToDAO(UIDeviceMindSetGroup d) ;
}
