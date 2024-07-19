/**
 * 
 */
package com.brijframework.client.unlimits.device.service;
import static com.brijframework.client.constants.ClientConstants.MY_UNLIMITS;

import java.util.List;
import java.util.Map;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientUnlimitsExampleItemMapper;
import com.brijframework.client.mapper.ClientUnlimitsExampleMapper;
import com.brijframework.client.repository.ClientUnlimitsExampleItemRepository;
import com.brijframework.client.repository.ClientUnlimitsExampleRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsExample;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsExampleItem;
import com.brijframework.client.unlimits.model.UIClientUnlimitsExample;

/**
 * @author omnie
 */
@Service
public class DeviceClientUnlimitsExampleServiceImpl extends CrudServiceImpl<UIClientUnlimitsExample, EOClientUnlimitsExample, Long>
		implements DeviceClientUnlimitsExampleService {

	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private ClientUnlimitsExampleRepository clientUnlimitsExampleRepository;
	
	@Autowired
	private ClientUnlimitsExampleMapper clientUnlimitsExampleMapper;
	
	@Autowired
	private ClientUnlimitsExampleItemRepository clientUnlimitsExampleItemRepository;
	
	@Autowired
	private ClientUnlimitsExampleItemMapper clientUnlimitsExampleItemMapper;

	@Override
	public JpaRepository<EOClientUnlimitsExample, Long> getRepository() {
		return clientUnlimitsExampleRepository;
	}

	@Override
	public GenericMapper<EOClientUnlimitsExample, UIClientUnlimitsExample> getMapper() {
		return clientUnlimitsExampleMapper;
	}

	@Override
	public void preAdd(UIClientUnlimitsExample data, EOClientUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsExampleRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postAdd(UIClientUnlimitsExample data, EOClientUnlimitsExample entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsExample(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIClientUnlimitsExample data, EOClientUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsExampleRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postUpdate(UIClientUnlimitsExample data, EOClientUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsExample(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UIClientUnlimitsExample dtoObject, EOClientUnlimitsExample entityObject,
			UIClientUnlimitsExample updateDtoObject, EOClientUnlimitsExample updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientUnlimitsExampleItem> exampleItems = clientUnlimitsExampleItemMapper.mapToDAO(dtoObject.getExampleItems());
		exampleItems.forEach(item->item.setUnlimitsExample(updateEntityObject));
		List<EOClientUnlimitsExampleItem> exampleItemsReturn = clientUnlimitsExampleItemRepository.saveAll(exampleItems);
		updateDtoObject.setExampleItems(clientUnlimitsExampleItemMapper.mapToDTO(exampleItemsReturn));
	}
	
	@Override
	public UIClientUnlimitsExample getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsExampleMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsExample());
	}
	
	@Override
	public List<EOClientUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOClientUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOClientUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
