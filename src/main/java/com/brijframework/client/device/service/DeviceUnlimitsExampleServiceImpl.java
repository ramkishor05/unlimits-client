/**
 * 
 */
package com.brijframework.client.device.service;
import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.DEVICE_DATE_FORMAT_MMMM_DD_YYYY;
import static com.brijframework.client.constants.Constants.INVALID_CLIENT;
import static com.brijframework.client.constants.Constants.MY_UNLIMITS;

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

import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.device.mapper.DeviceUnlimitsExampleItemMapper;
import com.brijframework.client.device.mapper.DeviceUnlimitsExampleMapper;
import com.brijframework.client.device.model.UIDeviceUnlimitsExample;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOUnlimitsExample;
import com.brijframework.client.entities.EOUnlimitsExampleItem;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.repository.UnlimitsExampleItemRepository;
import com.brijframework.client.repository.UnlimitsExampleRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceUnlimitsExampleServiceImpl extends CrudServiceImpl<UIDeviceUnlimitsExample, EOUnlimitsExample, Long>
		implements DeviceUnlimitsExampleService {

	private static final String EXAMPLE_DATE = "exampleDate";

	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private UnlimitsExampleRepository clientUnlimitsExampleRepository;
	
	@Autowired
	private DeviceUnlimitsExampleMapper clientUnlimitsExampleMapper;
	
	@Autowired
	private UnlimitsExampleItemRepository clientUnlimitsExampleItemRepository;
	
	@Autowired
	private DeviceUnlimitsExampleItemMapper clientUnlimitsExampleItemMapper;

	@Override
	public JpaRepository<EOUnlimitsExample, Long> getRepository() {
		return clientUnlimitsExampleRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsExample, UIDeviceUnlimitsExample> getMapper() {
		return clientUnlimitsExampleMapper;
	}
	
	{
		CustomPredicate<EOUnlimitsExample> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOUnlimitsExample> exampleDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> exampleDatePath = root.get(EXAMPLE_DATE);
			In<Object> exampleDateIn = criteriaBuilder.in(exampleDatePath);
			DateFormat timeFormat = new SimpleDateFormat(DEVICE_DATE_FORMAT_MMMM_DD_YYYY);
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
	public void preAdd(UIDeviceUnlimitsExample data, EOUnlimitsExample entity, Map<String, List<String>> headers) {
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
	public void postAdd(UIDeviceUnlimitsExample data, EOUnlimitsExample entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		eoCustBusinessApp.setUnlimitsExample(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIDeviceUnlimitsExample data, EOUnlimitsExample entity, Map<String, List<String>> headers) {
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
	public void postUpdate(UIDeviceUnlimitsExample data, EOUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		eoCustBusinessApp.setUnlimitsExample(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UIDeviceUnlimitsExample dtoObject, EOUnlimitsExample entityObject,
			UIDeviceUnlimitsExample updateDtoObject, EOUnlimitsExample updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOUnlimitsExampleItem> exampleItems = clientUnlimitsExampleItemMapper.mapToDAO(dtoObject.getExampleItems());
		exampleItems.forEach(item->item.setUnlimitsExample(updateEntityObject));
		List<EOUnlimitsExampleItem> exampleItemsReturn = clientUnlimitsExampleItemRepository.saveAll(exampleItems);
		updateDtoObject.setExampleItems(clientUnlimitsExampleItemMapper.mapToDTO(exampleItemsReturn));
	}
	
	@Override
	public UIDeviceUnlimitsExample getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		return clientUnlimitsExampleMapper.mapToDTO(eoCustBusinessApp.getUnlimitsExample());
	}
	
	@Override
	public void postFetch(EOUnlimitsExample findObject, UIDeviceUnlimitsExample dtoObject) {
		dtoObject.setType(UnlimitsType.EXAMPLE);
	}
	
	@Override
	public List<EOUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOUnlimitsExample> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
