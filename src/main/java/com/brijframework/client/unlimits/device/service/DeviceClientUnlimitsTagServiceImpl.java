/**
 * 
 */
package com.brijframework.client.unlimits.device.service;

import static com.brijframework.client.constants.ClientConstants.CUST_BUSINESS_APP;
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
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientUnlimitsTagItemMapper;
import com.brijframework.client.mapper.CustUnlimitsTagMapper;
import com.brijframework.client.repository.ClientUnlimitsTagItemRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.repository.CustUnlimitsTagRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsTagItem;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsTag;
import com.brijframework.client.unlimits.model.UICustUnlimitsTag;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceClientUnlimitsTagServiceImpl extends CrudServiceImpl<UICustUnlimitsTag, EOCustUnlimitsTag, Long>
		implements DeviceClientUnlimitsTagService {

	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;
	
	@Autowired
	private CustUnlimitsTagRepository clientUnlimitsTagRepository;
	
	@Autowired
	private CustUnlimitsTagMapper clientUnlimitsTagMapper;
	
	@Autowired
	private ClientUnlimitsTagItemRepository clientUnlimitsTagItemRepository;
	
	@Autowired
	private ClientUnlimitsTagItemMapper clientUnlimitsTagItemMapper;
	
	@Override
	public JpaRepository<EOCustUnlimitsTag, Long> getRepository() {
		return clientUnlimitsTagRepository;
	}

	@Override
	public GenericMapper<EOCustUnlimitsTag, UICustUnlimitsTag> getMapper() {
		return clientUnlimitsTagMapper;
	}
	
	{
		CustomPredicate<EOCustUnlimitsTag> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
	}

	@Override
	public void preAdd(UICustUnlimitsTag data, EOCustUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postAdd(UICustUnlimitsTag data, EOCustUnlimitsTag entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsTag(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UICustUnlimitsTag data, EOCustUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postUpdate(UICustUnlimitsTag data, EOCustUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsTag(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UICustUnlimitsTag dtoObject, EOCustUnlimitsTag entityObject,
			UICustUnlimitsTag updateDtoObject, EOCustUnlimitsTag updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientUnlimitsTagItem> mindSetItems = clientUnlimitsTagItemMapper.mapToDAO(dtoObject.getTagItems());
		mindSetItems.forEach(item->item.setUnlimitsTag(updateEntityObject));
		List<EOClientUnlimitsTagItem> tagItemsReturn = clientUnlimitsTagItemRepository.saveAll(mindSetItems);
		updateDtoObject.setTagItems(clientUnlimitsTagItemMapper.mapToDTO(tagItemsReturn));
	}
	
	@Override
	public UICustUnlimitsTag getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsTagMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsTag());
	}
	
	@Override
	public List<UICustUnlimitsTag> postFetch(List<EOCustUnlimitsTag> findObjects) {

		return super.postFetch(findObjects);
	}
	
	@Override
	public List<EOCustUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOCustUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOCustUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
