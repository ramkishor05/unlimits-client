package com.brijframework.client.global.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOReProgramGroup;
import com.brijframework.client.global.model.UIGlobalReProgramGroup;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalReProgramGroupMapper extends GenericMapper<EOReProgramGroup, UIGlobalReProgramGroup>{

	@Override
	@Mapping(target = "reprograms", ignore = true)
	EOReProgramGroup mapToDAO(UIGlobalReProgramGroup d);
}
