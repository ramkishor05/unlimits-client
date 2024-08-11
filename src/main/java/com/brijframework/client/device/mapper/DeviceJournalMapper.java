/**
 * 
 */
package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.DEVICE_DATE_FORMAT_MMMM_DD_YYYY;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.device.model.UIDeviceJournalItem;
import com.brijframework.client.entities.EOJournal;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceJournalMapper extends GenericMapper<EOJournal, UIDeviceJournalItem>{

	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = DEVICE_DATE_FORMAT_MMMM_DD_YYYY)
    UIDeviceJournalItem mapToDTO(EOJournal e) ;
	
	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = DEVICE_DATE_FORMAT_MMMM_DD_YYYY)
    EOJournal mapToDAO(UIDeviceJournalItem d);
}
