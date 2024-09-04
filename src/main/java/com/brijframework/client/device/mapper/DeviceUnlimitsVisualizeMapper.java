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
		if(e.getUnlimitsExample() !=null) {
			return UnlimitsType.EXAMPLE;
		}
		if(e.getUnlimitsImage() !=null) {
			return UnlimitsType.IMAGE;
		}
		if(e.getUnlimitsTag() !=null) {
			return UnlimitsType.WORDS;
		}
		return e.getType();
	}
	
	static Long getUnlimitsId(EOUnlimitsVisualize e){
		if(e.getUnlimitsExample() !=null) {
			return e.getUnlimitsExample().getId();
		}
		if(e.getUnlimitsImage() !=null) {
			return e.getUnlimitsImage().getId();
		}
		if(e.getUnlimitsTag() !=null) {
			return e.getUnlimitsTag().getId();
		}
		return null;
	}
}
