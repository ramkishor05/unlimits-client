/**
 * 
 */
package com.brijframework.client.mapper;

import static com.brijframework.client.constants.ClientConstants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.ClientConstants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.unlimits.entities.EOClientGoalGroup;
import com.brijframework.client.unlimits.model.UIClientGoalGroup;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientGoalGroupMapper extends GenericMapper<EOClientGoalGroup, UIClientGoalGroup>{

	@Override
    UIClientGoalGroup mapToDTO(EOClientGoalGroup e) ;
	
	@Override
	@Mapping(target = "goals", ignore = true)
    EOClientGoalGroup mapToDAO(UIClientGoalGroup d);
}
