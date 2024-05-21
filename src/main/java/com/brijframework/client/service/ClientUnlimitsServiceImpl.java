/**
 * 
 */
package com.brijframework.client.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOClientUnlimits;
import com.brijframework.client.mapper.ClientUnlimitsMapper;
import com.brijframework.client.model.ClientUnlimitsDTO;
import com.brijframework.client.repository.ClientUnlimitsRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;

import static com.brijframework.client.constants.ClientConstants.CUST_APP_ID;

/**
 * @author omnie
 */
@Service
public class ClientUnlimitsServiceImpl extends CrudServiceImpl<ClientUnlimitsDTO, EOClientUnlimits, Long>
		implements ClientUnlimitsService {

	@Autowired
	private ClientUnlimitsRepository clientUnlimitsRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

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

	@Override
	protected void preAdd(ClientUnlimitsDTO data, EOClientUnlimits entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(!CollectionUtils.isEmpty(list)) {
			entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		}
	}
}
