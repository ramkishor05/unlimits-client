/**
 * 
 */
package com.brijframework.client.mapper;

import static com.brijframework.client.constants.ClientConstants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.ClientConstants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.unlimits.entities.EOClientGoalItem;
import com.brijframework.client.unlimits.model.UIClientGoalItem;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientGoalItemMapper extends GenericMapper<EOClientGoalItem, UIClientGoalItem>{

	@Override
    UIClientGoalItem mapToDTO(EOClientGoalItem e) ;
	
	@Override
    EOClientGoalItem mapToDAO(UIClientGoalItem d);
}
