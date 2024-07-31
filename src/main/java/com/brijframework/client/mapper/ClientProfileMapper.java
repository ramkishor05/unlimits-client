package com.brijframework.client.mapper;

import static com.brijframework.client.constants.ClientConstants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.ClientConstants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.unlimits.entities.EOClientProfile;
import com.brijframework.client.unlimits.model.UIClientVisualize;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientProfileMapper extends GenericMapper<EOClientProfile, UIClientVisualize>{

	@Override
	EOClientProfile mapToDAO(UIClientVisualize d) ;
}
