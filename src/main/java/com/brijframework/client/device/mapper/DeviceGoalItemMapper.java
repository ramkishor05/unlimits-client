/**
 * 
 */
package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceGoalItem;
import com.brijframework.client.entities.EOGoalItem;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceGoalItemMapper extends GenericMapper<EOGoalItem, UIDeviceGoalItem>{

	@Override
    UIDeviceGoalItem mapToDTO(EOGoalItem e) ;
	
	@Override
    EOGoalItem mapToDAO(UIDeviceGoalItem d);
}
