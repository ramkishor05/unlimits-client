/**
 * 
 */
package com.brijframework.client.global.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOGoalGroup;
import com.brijframework.client.global.model.UIGlobalGoalGroup;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalGoalGroupMapper extends GenericMapper<EOGoalGroup, UIGlobalGoalGroup>{

	@Override
    UIGlobalGoalGroup mapToDTO(EOGoalGroup e) ;
	
	@Override
	@Mapping(target = "goals", ignore = true)
    EOGoalGroup mapToDAO(UIGlobalGoalGroup d);
}
