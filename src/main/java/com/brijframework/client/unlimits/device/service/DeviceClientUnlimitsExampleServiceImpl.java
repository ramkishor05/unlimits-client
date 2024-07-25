/**
 * 
 */
package com.brijframework.client.unlimits.device.service;
import static com.brijframework.client.constants.ClientConstants.MY_UNLIMITS;
import static com.brijframework.client.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;
import static com.brijframework.client.constants.ClientConstants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.ClientConstants.INVALID_CLIENT;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.brijframework.client.mapper.ClientUnlimitsExampleItemMapper;
import com.brijframework.client.mapper.ClientUnlimitsExampleMapper;
import com.brijframework.client.repository.ClientUnlimitsExampleItemRepository;
import com.brijframework.client.repository.ClientUnlimitsExampleRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsExample;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsExampleItem;
import com.brijframework.client.unlimits.model.UICustUnlimitsExample;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceClientUnlimitsExampleServiceImpl extends CrudServiceImpl<UICustUnlimitsExample, EOCustUnlimitsExample, Long>
		implements DeviceClientUnlimitsExampleService {

	private static final String EXAMPLE_DATE = "exampleDate";

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
	public JpaRepository<EOCustUnlimitsExample, Long> getRepository() {
		return clientUnlimitsExampleRepository;
	}

	@Override
	public GenericMapper<EOCustUnlimitsExample, UICustUnlimitsExample> getMapper() {
		return clientUnlimitsExampleMapper;
	}
	
	{
		CustomPredicate<EOCustUnlimitsExample> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOCustUnlimitsExample> exampleDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> exampleDatePath = root.get(EXAMPLE_DATE);
			In<Object> exampleDateIn = criteriaBuilder.in(exampleDatePath);
			DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY);
			Date date = null;
			try {
				date = timeFormat.parse(filter.getColumnValue().toString());
			} catch (ParseException e) {
				System.err.println("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue());
			}
			exampleDateIn.value(new java.sql.Date(date.getTime()) );
			return exampleDateIn;
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(EXAMPLE_DATE, exampleDate);
	}

	@Override
	public void preAdd(UICustUnlimitsExample data, EOCustUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsExampleRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postAdd(UICustUnlimitsExample data, EOCustUnlimitsExample entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		eoCustBusinessApp.setClientUnlimitsExample(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UICustUnlimitsExample data, EOCustUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsExampleRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postUpdate(UICustUnlimitsExample data, EOCustUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		eoCustBusinessApp.setClientUnlimitsExample(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UICustUnlimitsExample dtoObject, EOCustUnlimitsExample entityObject,
			UICustUnlimitsExample updateDtoObject, EOCustUnlimitsExample updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientUnlimitsExampleItem> exampleItems = clientUnlimitsExampleItemMapper.mapToDAO(dtoObject.getExampleItems());
		exampleItems.forEach(item->item.setUnlimitsExample(updateEntityObject));
		List<EOClientUnlimitsExampleItem> exampleItemsReturn = clientUnlimitsExampleItemRepository.saveAll(exampleItems);
		updateDtoObject.setExampleItems(clientUnlimitsExampleItemMapper.mapToDTO(exampleItemsReturn));
	}
	
	@Override
	public UICustUnlimitsExample getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		return clientUnlimitsExampleMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsExample());
	}
	
	@Override
	public List<EOCustUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOCustUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOCustUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
