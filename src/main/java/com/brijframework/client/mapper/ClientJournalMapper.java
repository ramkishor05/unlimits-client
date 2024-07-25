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

import com.brijframework.client.unlimits.entities.EOCustJournal;
import com.brijframework.client.unlimits.model.UICustJournalItem;
import static com.brijframework.client.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientJournalMapper extends GenericMapper<EOCustJournal, UICustJournalItem>{

	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = UI_DATE_FORMAT_MMMM_DD_YYYY)
    UICustJournalItem mapToDTO(EOCustJournal e) ;
	
	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = UI_DATE_FORMAT_MMMM_DD_YYYY)
    EOCustJournal mapToDAO(UICustJournalItem d);
}
