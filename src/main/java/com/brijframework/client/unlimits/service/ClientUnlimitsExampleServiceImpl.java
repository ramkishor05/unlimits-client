/**
 * 
 */
package com.brijframework.client.unlimits.service;

import static com.brijframework.client.constants.ClientConstants.CUST_APP_ID;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientUnlimitsExampleMapper;
import com.brijframework.client.repository.ClientUnlimitsExampleRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsExample;
import com.brijframework.client.unlimits.model.UIClientUnlimitsExample;

/**
 * @author omnie
 */
@Service
public class ClientUnlimitsExampleServiceImpl extends CrudServiceImpl<UIClientUnlimitsExample, EOClientUnlimitsExample, Long>
		implements ClientUnlimitsExampleService {

	@Autowired
	private ClientUnlimitsExampleRepository clientUnlimitsExampleRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private ClientUnlimitsExampleMapper clientUnlimitsExampleMapper;

	@Override
	public JpaRepository<EOClientUnlimitsExample, Long> getRepository() {
		return clientUnlimitsExampleRepository;
	}

	@Override
	public GenericMapper<EOClientUnlimitsExample, UIClientUnlimitsExample> getMapper() {
		return clientUnlimitsExampleMapper;
	}

	@Override
	protected void preAdd(UIClientUnlimitsExample data, EOClientUnlimitsExample entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
	}
	
	@Override
	protected void preUpdate(UIClientUnlimitsExample data, EOClientUnlimitsExample entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		//entity.getCustBusinessApp().setClientUnlimitsExample(entity);
		//entity.getItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
	}
	
	@Override
	public UIClientUnlimitsExample getCurrent( Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientUnlimitsExampleMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsExample());
	}
	
	@Override
	protected List<EOClientUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientUnlimitsExampleRepository.findAllByCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	protected Page<EOClientUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientUnlimitsExampleRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}
	
	@Override
	protected List<EOClientUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Sort sort) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientUnlimitsExampleRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}
}
