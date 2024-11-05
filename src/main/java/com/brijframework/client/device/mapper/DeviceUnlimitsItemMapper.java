/**
 * 
 */
package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceUnlimitsItem;
import com.brijframework.client.entities.EOUnlimitsItem;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceUnlimitsItemMapper extends GenericMapper<EOUnlimitsItem, UIDeviceUnlimitsItem>{

	@Mapping(target = "unlimitsId", source = "unlimits.id")
	@Override
	UIDeviceUnlimitsItem mapToDTO(EOUnlimitsItem e) ;
	
	@Mapping(target = "unlimits.id", source = "unlimitsId")
	@Override
	EOUnlimitsItem mapToDAO(UIDeviceUnlimitsItem d);
}
