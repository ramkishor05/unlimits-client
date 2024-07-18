/**
 * 
 */
package com.brijframework.client.mapper;

import static com.brijframework.client.constants.ClientConstants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.ClientConstants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.unlimits.entities.EOClientCommitmentItem;
import com.brijframework.client.unlimits.model.UIClientCommitmentItem;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientCommitmentItemMapper extends GenericMapper<EOClientCommitmentItem, UIClientCommitmentItem>{

	@Override
    UIClientCommitmentItem mapToDTO(EOClientCommitmentItem e) ;
	
	@Override
    EOClientCommitmentItem mapToDAO(UIClientCommitmentItem d);
}