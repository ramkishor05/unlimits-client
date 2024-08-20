package com.brijframework.client.global.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOUnlimitsCoachConversion;
import com.brijframework.client.global.model.UIUnlimitsCoachConversion;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalUnlimitsCoachConversionMapper extends GenericMapper<EOUnlimitsCoachConversion, UIUnlimitsCoachConversion>{

	@Override
	EOUnlimitsCoachConversion mapToDAO(UIUnlimitsCoachConversion d) ;
}
