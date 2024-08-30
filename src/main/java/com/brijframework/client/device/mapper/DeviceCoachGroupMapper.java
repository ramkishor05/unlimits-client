package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.DEVICE_DATE_FORMAT_MMMM_DD_YYYY;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIUnlimitsCoachConversion;
import com.brijframework.client.entities.EOUnlimitsCoachConversion;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceCoachGroupMapper extends GenericMapper<EOUnlimitsCoachConversion, UIUnlimitsCoachConversion> {

	@Override
	@Mapping(target = "coachDate" , source = "coachDate", dateFormat = DEVICE_DATE_FORMAT_MMMM_DD_YYYY)
	EOUnlimitsCoachConversion mapToDAO(UIUnlimitsCoachConversion d);

	@Override
	@Mapping(target = "coachDate" , source = "coachDate", dateFormat = DEVICE_DATE_FORMAT_MMMM_DD_YYYY)
	UIUnlimitsCoachConversion mapToDTO(EOUnlimitsCoachConversion e);

}
