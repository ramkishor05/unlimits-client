/**
 * 
 */
package com.brijframework.client.global.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOGoalItem;
import com.brijframework.client.global.model.UIGlobalGoalItem;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalGoalItemMapper extends GenericMapper<EOGoalItem, UIGlobalGoalItem>{

	@Override
    UIGlobalGoalItem mapToDTO(EOGoalItem e) ;
	
	@Override
    EOGoalItem mapToDAO(UIGlobalGoalItem d);
}
