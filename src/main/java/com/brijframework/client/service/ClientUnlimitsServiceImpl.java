/**
 * 
 */
package com.brijframework.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOClientUnlimits;
import com.brijframework.client.mapper.ClientUnlimitsMapper;
import com.brijframework.client.model.ClientUnlimitsDTO;
import com.brijframework.client.repository.ClientUnlimitsRepository;

/**
 *  @author omnie
 */
@Service
public class ClientUnlimitsServiceImpl extends CrudServiceImpl<ClientUnlimitsDTO, EOClientUnlimits, Long> implements ClientUnlimitsService{
	
	@Autowired
	private ClientUnlimitsRepository clientUnlimitsRepository;
	
	@Autowired
	private ClientUnlimitsMapper clientUnlimitsMapper;

	@Override
	public JpaRepository<EOClientUnlimits, Long> getRepository() {
		return clientUnlimitsRepository;
	}

	@Override
	public GenericMapper<EOClientUnlimits, ClientUnlimitsDTO> getMapper() {
		return clientUnlimitsMapper;
	}

}
