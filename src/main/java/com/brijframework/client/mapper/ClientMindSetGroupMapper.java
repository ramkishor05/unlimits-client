package com.brijframework.client.mapper;

import static com.brijframework.client.constants.ClientConstants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.ClientConstants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.unlimits.entities.EOClientMindSetGroup;
import com.brijframework.client.unlimits.model.UIClientMindSetGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMindSetGroupMapper extends GenericMapper<EOClientMindSetGroup, UIClientMindSetGroup>{

	@Override
	@Mapping(target = "mindSets", ignore = true)
	EOClientMindSetGroup mapToDAO(UIClientMindSetGroup d) ;
}
