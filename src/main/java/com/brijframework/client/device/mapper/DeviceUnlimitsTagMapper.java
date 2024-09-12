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

import com.brijframework.client.device.model.UIDeviceUnlimitsTag;
import com.brijframework.client.entities.EOUnlimitsTag;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceUnlimitsTagMapper extends GenericMapper<EOUnlimitsTag, UIDeviceUnlimitsTag>{

	@Mapping(target = "date", source = "date", dateFormat = UI_DATE_FORMAT_MM_DD_YY)
	@Override
	UIDeviceUnlimitsTag mapToDTO(EOUnlimitsTag e) ;
	
	@Mapping(target = "date", source = "date", dateFormat = UI_DATE_FORMAT_MM_DD_YY)
	@Override
    EOUnlimitsTag mapToDAO(UIDeviceUnlimitsTag d);
}
