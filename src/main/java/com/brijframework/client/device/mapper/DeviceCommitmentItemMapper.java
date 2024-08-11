/**
 * 
 */
package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceCommitmentItem;
import com.brijframework.client.entities.EOCommitmentItem;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceCommitmentItemMapper extends GenericMapper<EOCommitmentItem, UIDeviceCommitmentItem>{

	@Override
    UIDeviceCommitmentItem mapToDTO(EOCommitmentItem e) ;
	
	@Override
    EOCommitmentItem mapToDAO(UIDeviceCommitmentItem d);
}