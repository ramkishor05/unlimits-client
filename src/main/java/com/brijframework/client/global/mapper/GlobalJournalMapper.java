/**
 * 
 */
package com.brijframework.client.global.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;
import static com.brijframework.client.constants.Constants.DEVICE_DATE_FORMAT_MMMM_DD_YYYY;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOJournal;
import com.brijframework.client.global.model.UIGlobalJournalItem;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalJournalMapper extends GenericMapper<EOJournal, UIGlobalJournalItem>{

	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = DEVICE_DATE_FORMAT_MMMM_DD_YYYY)
    UIGlobalJournalItem mapToDTO(EOJournal e) ;
	
	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = DEVICE_DATE_FORMAT_MMMM_DD_YYYY)
    EOJournal mapToDAO(UIGlobalJournalItem d);
}
