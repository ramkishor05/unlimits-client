package com.brijframework.client.mapper;

import static com.brijframework.client.constants.ClientConstants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.ClientConstants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.unlimits.entities.EOClientReProgramGroup;
import com.brijframework.client.unlimits.model.UIClientReProgramGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientReProgramGroupMapper extends GenericMapper<EOClientReProgramGroup, UIClientReProgramGroup>{

	@Override
	@Mapping(target = "reprograms", ignore = true)
	EOClientReProgramGroup mapToDAO(UIClientReProgramGroup d);
}
