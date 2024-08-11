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

import com.brijframework.client.device.model.UIDeviceGoalGroup;
import com.brijframework.client.entities.EOGoalGroup;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceGoalGroupMapper extends GenericMapper<EOGoalGroup, UIDeviceGoalGroup>{

	@Override
    UIDeviceGoalGroup mapToDTO(EOGoalGroup e) ;
	
	@Override
	@Mapping(target = "goals", ignore = true)
    EOGoalGroup mapToDAO(UIDeviceGoalGroup d);
}
