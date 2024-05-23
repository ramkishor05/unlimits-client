/**
 * 
 */
package com.brijframework.client.visualise.service;

import static com.brijframework.client.constants.ClientConstants.CUST_APP_ID;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientVisualiseExampleMapper;
import com.brijframework.client.repository.ClientVisualiseExampleRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.visualise.entities.EOClientVisualiseExample;
import com.brijframework.client.visualise.model.UIClientVisualiseExample;

/**
 * @author omnie
 */
@Service
public class ClientVisualiseExampleServiceImpl extends CrudServiceImpl<UIClientVisualiseExample, EOClientVisualiseExample, Long>
		implements ClientVisualiseExampleService {

	@Autowired
	private ClientVisualiseExampleRepository clientVisualiseExampleRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private ClientVisualiseExampleMapper clientVisualiseExampleMapper;

	@Override
	public JpaRepository<EOClientVisualiseExample, Long> getRepository() {
		return clientVisualiseExampleRepository;
	}

	@Override
	public GenericMapper<EOClientVisualiseExample, UIClientVisualiseExample> getMapper() {
		return clientVisualiseExampleMapper;
	}

	@Override
	protected void preAdd(UIClientVisualiseExample data, EOClientVisualiseExample entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
	}
	
	@Override
	protected void preUpdate(UIClientVisualiseExample data, EOClientVisualiseExample entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		//entity.getCustBusinessApp().setClientVisualiseExample(entity);
		//entity.getItems().forEach(tagItem->tagItem.setVisualiseTag(entity));
	}
	
	@Override
	public UIClientVisualiseExample getCurrent( Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientVisualiseExampleMapper.mapToDTO(eoCustBusinessApp.getClientVisualiseExample());
	}
}
