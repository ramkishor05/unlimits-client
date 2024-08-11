package com.brijframework.client.global.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOCoachGroup;
import com.brijframework.client.global.model.UIGlobalCoachGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalCoachGroupMapper extends GenericMapper<EOCoachGroup, UIGlobalCoachGroup>{

	@Override
	@Mapping(target = "coachs", ignore = true)
	EOCoachGroup mapToDAO(UIGlobalCoachGroup d) ;
}
