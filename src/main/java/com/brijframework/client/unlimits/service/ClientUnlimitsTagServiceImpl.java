/**
 * 
 */
package com.brijframework.client.unlimits.service;

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
import com.brijframework.client.mapper.ClientUnlimitsTagMapper;
import com.brijframework.client.repository.ClientUnlimitsTagRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsTag;
import com.brijframework.client.unlimits.model.UIClientUnlimitsTag;

/**
 * @author omnie
 */
@Service
public class ClientUnlimitsTagServiceImpl extends CrudServiceImpl<UIClientUnlimitsTag, EOClientUnlimitsTag, Long>
		implements ClientUnlimitsTagService {

	@Autowired
	private ClientUnlimitsTagRepository clientUnlimitsTagRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private ClientUnlimitsTagMapper clientUnlimitsTagMapper;

	@Override
	public JpaRepository<EOClientUnlimitsTag, Long> getRepository() {
		return clientUnlimitsTagRepository;
	}

	@Override
	public GenericMapper<EOClientUnlimitsTag, UIClientUnlimitsTag> getMapper() {
		return clientUnlimitsTagMapper;
	}

	@Override
	protected void preAdd(UIClientUnlimitsTag data, EOClientUnlimitsTag entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		entity.getCustBusinessApp().setClientUnlimitsTag(entity);
		entity.getTagItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
	}
	
	@Override
	protected void preUpdate(UIClientUnlimitsTag data, EOClientUnlimitsTag entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		entity.getTagItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
	}
	
	@Override
	public UIClientUnlimitsTag getCurrent( Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientUnlimitsTagMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsTag());
	}
}
