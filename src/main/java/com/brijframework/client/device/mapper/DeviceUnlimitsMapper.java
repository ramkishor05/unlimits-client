/**
 * 
 */
package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;
import static com.brijframework.client.constants.Constants.UI_DATE_FORMAT_MM_DD_YY;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.entities.EOUnlimits;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceUnlimitsMapper extends GenericMapper<EOUnlimits, UIDeviceUnlimits>{

	@Mapping(target = "date", source = "date", dateFormat = UI_DATE_FORMAT_MM_DD_YY)
	@Override
	@Mapping(target = "items", ignore = true)
	UIDeviceUnlimits mapToDTO(EOUnlimits e) ;
	
	@Mapping(target = "date", source = "date", dateFormat = UI_DATE_FORMAT_MM_DD_YY)
	@Mapping(target = "items", ignore = true)
	@Override
    EOUnlimits mapToDAO(UIDeviceUnlimits d);
}
