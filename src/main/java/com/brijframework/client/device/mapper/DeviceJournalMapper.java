/**
 * 
 */
package com.brijframework.client.device.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.UI_DATE_FORMAT_MM_DD_YY;
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
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = UI_DATE_FORMAT_MM_DD_YY)
    UIDeviceJournalItem mapToDTO(EOJournal e) ;
	
	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = UI_DATE_FORMAT_MM_DD_YY)
    EOJournal mapToDAO(UIDeviceJournalItem d);
}
