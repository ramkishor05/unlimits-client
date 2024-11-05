package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;
import static com.brijframework.client.constants.Constants.UI_DATE_FORMAT_MM_DD_YY;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceCoachChat;
import com.brijframework.client.entities.EOCoachChat;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceCoachChatMapper extends GenericMapper<EOCoachChat, UIDeviceCoachChat> {

	@Override
	@Mapping(target = "coachDate" , source = "coachDate", dateFormat = UI_DATE_FORMAT_MM_DD_YY)
	@Mapping(target = "coachSession.id" , source = "coachSessionId")
	EOCoachChat mapToDAO(UIDeviceCoachChat d);

	@Override
	@Mapping(target = "coachDate" , source = "coachDate", dateFormat = UI_DATE_FORMAT_MM_DD_YY)
	@Mapping(target = "coachSessionId" , source = "coachSession.id")
	UIDeviceCoachChat mapToDTO(EOCoachChat e);

}
