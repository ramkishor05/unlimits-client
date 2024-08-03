/**
 * 
 */
package com.brijframework.client.unlimits.device.service;
import static com.brijframework.client.constants.ClientConstants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.ClientConstants.INVALID_CLIENT;
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

import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientUnlimitsImageItemMapper;
import com.brijframework.client.mapper.ClientUnlimitsImageMapper;
import com.brijframework.client.repository.ClientUnlimitsImageItemRepository;
import com.brijframework.client.repository.ClientUnlimitsImageRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsImageItem;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsImage;
import com.brijframework.client.unlimits.model.UICustUnlimitsImage;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceClientUnlimitsImageServiceImpl extends CrudServiceImpl<UICustUnlimitsImage, EOCustUnlimitsImage, Long>
		implements DeviceClientUnlimitsImageService {

	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;


	@Autowired
	private ClientUnlimitsImageRepository clientUnlimitsImageRepository;
	
	@Autowired
	private ClientUnlimitsImageMapper clientUnlimitsImageMapper;
	

	@Autowired
	private ClientUnlimitsImageItemRepository clientUnlimitsImageItemRepository;
	

	@Autowired
	private ClientUnlimitsImageItemMapper clientUnlimitsImageItemMapper;

	@Override
	public JpaRepository<EOCustUnlimitsImage, Long> getRepository() {
		return clientUnlimitsImageRepository;
	}

	@Override
	public GenericMapper<EOCustUnlimitsImage, UICustUnlimitsImage> getMapper() {
		return clientUnlimitsImageMapper;
	}
	
	{
		CustomPredicate<EOCustUnlimitsImage> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
	}

	@Override
	public void preAdd(UICustUnlimitsImage data, EOCustUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsImageRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postAdd(UICustUnlimitsImage data, EOCustUnlimitsImage entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		eoCustBusinessApp.setClientUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UICustUnlimitsImage data, EOCustUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsImageRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postUpdate(UICustUnlimitsImage data, EOCustUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		eoCustBusinessApp.setClientUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UICustUnlimitsImage dtoObject, EOCustUnlimitsImage entityObject,
			UICustUnlimitsImage updateDtoObject, EOCustUnlimitsImage updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientUnlimitsImageItem> imageItems = clientUnlimitsImageItemMapper.mapToDAO(dtoObject.getImageItems());
		imageItems.forEach(item->item.setUnlimitsImage(updateEntityObject));
		List<EOClientUnlimitsImageItem> imageItemsReturn = clientUnlimitsImageItemRepository.saveAll(imageItems);
		updateDtoObject.setImageItems(clientUnlimitsImageItemMapper.mapToDTO(imageItemsReturn));
	}
	
	@Override
	public UICustUnlimitsImage getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		return clientUnlimitsImageMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsImage());
	}
	

	@Override
	public void postFetch(EOCustUnlimitsImage findObject, UICustUnlimitsImage dtoObject) {
		dtoObject.setType(UnlimitsType.IMAGE);
	}
	
	@Override
	public List<EOCustUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOCustUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOCustUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
