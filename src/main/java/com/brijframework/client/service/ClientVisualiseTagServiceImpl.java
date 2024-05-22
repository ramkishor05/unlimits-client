/**
 * 
 */
package com.brijframework.client.service;

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
import com.brijframework.client.entities.visualise.EOClientVisualiseTag;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientVisualiseTagMapper;
import com.brijframework.client.model.visualise.UIClientVisualiseTag;
import com.brijframework.client.repository.ClientVisualiseTagRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;

/**
 * @author omnie
 */
@Service
public class ClientVisualiseTagServiceImpl extends CrudServiceImpl<UIClientVisualiseTag, EOClientVisualiseTag, Long>
		implements ClientVisualiseTagService {

	@Autowired
	private ClientVisualiseTagRepository clientVisualiseTagRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private ClientVisualiseTagMapper clientVisualiseTagMapper;

	@Override
	public JpaRepository<EOClientVisualiseTag, Long> getRepository() {
		return clientVisualiseTagRepository;
	}

	@Override
	public GenericMapper<EOClientVisualiseTag, UIClientVisualiseTag> getMapper() {
		return clientVisualiseTagMapper;
	}

	@Override
	protected void preAdd(UIClientVisualiseTag data, EOClientVisualiseTag entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		entity.getCustBusinessApp().setClientVisualiseTag(entity);
		entity.getTagItems().forEach(tagItem->tagItem.setVisualiseTag(entity));
	}
	
	@Override
	protected void preUpdate(UIClientVisualiseTag data, EOClientVisualiseTag entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		entity.getTagItems().forEach(tagItem->tagItem.setVisualiseTag(entity));
	}
	
	@Override
	public UIClientVisualiseTag getCurrent( Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientVisualiseTagMapper.mapToDTO(eoCustBusinessApp.getClientVisualiseTag());
	}
}
