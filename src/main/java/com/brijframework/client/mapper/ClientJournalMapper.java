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
import com.brijframework.client.unlimits.model.UIClientJournal;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientJournalMapper extends GenericMapper<EOClientJournal, UIClientJournal>{

	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = "MM/dd/yyyy")
    UIClientJournal mapToDTO(EOClientJournal e) ;
	
	@Override
	@Mapping(target = "journalDate" , source = "journalDate", dateFormat = "MM/dd/yyyy")
    EOClientJournal mapToDAO(UIClientJournal d);
}
