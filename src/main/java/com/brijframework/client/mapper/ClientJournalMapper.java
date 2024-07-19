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

import com.brijframework.client.unlimits.entities.EOClientJournal;
import com.brijframework.client.unlimits.model.UIClientJournalItem;
import static com.brijframework.client.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientJournalMapper extends GenericMapper<EOClientJournal, UIClientJournalItem>{

	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = UI_DATE_FORMAT_MMMM_DD_YYYY)
    UIClientJournalItem mapToDTO(EOClientJournal e) ;
	
	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = UI_DATE_FORMAT_MMMM_DD_YYYY)
    EOClientJournal mapToDAO(UIClientJournalItem d);
}
