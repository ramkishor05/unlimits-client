package com.brijframework.client.global.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOMindSetGroup;
import com.brijframework.client.global.model.UIGlobalMindSetGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalMindSetGroupMapper extends GenericMapper<EOMindSetGroup, UIGlobalMindSetGroup>{

	@Override
	@Mapping(target = "mindSets", ignore = true)
	EOMindSetGroup mapToDAO(UIGlobalMindSetGroup d) ;
}
