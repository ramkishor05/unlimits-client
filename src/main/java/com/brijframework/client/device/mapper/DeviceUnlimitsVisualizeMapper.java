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

import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.entities.EOUnlimitsVisualize;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceUnlimitsVisualizeMapper extends GenericMapper<EOUnlimitsVisualize, UIDeviceUnlimitsVisualize>{

	@Override
	@Mapping(target = "type", expression = "java(DeviceUnlimitsVisualizeMapper.getType(e))")
	@Mapping(target = "unlimitId", expression = "java(DeviceUnlimitsVisualizeMapper.getUnlimitsId(e))")
	UIDeviceUnlimitsVisualize mapToDTO(EOUnlimitsVisualize e);
	
	static UnlimitsType getType(EOUnlimitsVisualize e){
		return e.getType();
	}
	
	static Long getUnlimitsId(EOUnlimitsVisualize e){
		switch (e.getType()) {
			case WORDS: {
				return e.getUnlimitsTag().getId();
			}
			case IMAGE: {
				return e.getUnlimitsImage().getId();
			}
			case EXAMPLE: {
				return e.getUnlimitsExample().getId();
			}
		}
		return null;
	}
}
