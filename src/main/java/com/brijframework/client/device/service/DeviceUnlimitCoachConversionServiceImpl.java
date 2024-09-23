package com.brijframework.client.device.service;

import static com.brijframework.client.constants.Constants.COACH_DATE;
import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.UI_DATE_FORMAT_MM_DD_YY;
import static com.brijframework.client.constants.Constants.INVALID_CLIENT;
import static org.unlimits.rest.constants.RestConstant.ORDER_BY;
import static org.unlimits.rest.constants.RestConstant.SORT_ORDER;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.brijframework.util.casting.DateUtil;
import org.brijframework.util.reflect.FieldUtil;
import org.brijframework.util.support.ReflectionAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceCoachGroupMapper;
import com.brijframework.client.device.model.UIUnlimitsCoachConversion;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;
import com.brijframework.client.entities.EOUnlimitsCoachConversion;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.CoachLibararyRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

@Service
public class DeviceUnlimitCoachConversionServiceImpl extends CrudServiceImpl<UIUnlimitsCoachConversion, EOUnlimitsCoachConversion, Long> implements DeviceUnlimitCoachConversionService {
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceUnlimitCoachConversionServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private CoachLibararyRepository coachLibararyRepository;
	
	@Autowired
	private DeviceCoachGroupMapper deviceCoachLibararyMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOUnlimitsCoachConversion, Long> getRepository() {
		return coachLibararyRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsCoachConversion, UIUnlimitsCoachConversion> getMapper() {
		return deviceCoachLibararyMapper;
	}
	
	{
		CustomPredicate<EOUnlimitsCoachConversion> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
				In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
				custBusinessAppIn.value(filter.getColumnValue());
				return custBusinessAppIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for custBusinessApp: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		CustomPredicate<EOUnlimitsCoachConversion> coachDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Object> coachDatePath = root.get(COACH_DATE);
				In<Object> coachDateIn = criteriaBuilder.in(coachDatePath);
				coachDateIn.value(DateUtil.dateObject(filter.getColumnValue().toString(), UI_DATE_FORMAT_MM_DD_YY));
				return coachDateIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception in coachDate: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(COACH_DATE, coachDate);
	}

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		ignoreProperties.add(CUST_BUSINESS_APP);
		return ignoreProperties;
	}

	
	@Override
	public void preAdd(UIUnlimitsCoachConversion data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preAdd(UIUnlimitsCoachConversion data, EOUnlimitsCoachConversion entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setRecordState(RecordStatus.ACTIVETED.getStatus());
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIUnlimitsCoachConversion data, EOUnlimitsCoachConversion entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setRecordState(RecordStatus.ACTIVETED.getStatus());
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public List<UIUnlimitsCoachConversion> postFetch(List<EOUnlimitsCoachConversion> findObjects) {
		List<UIUnlimitsCoachConversion> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getCoachDate().compareTo(op2.getCoachDate()));
		return uiObjects;
	}
	
	@Override
	public Boolean deleteById(Long uuid) {
		EOUnlimitsCoachConversion eoUnlimitsCoachConversion = find(uuid);
		if(eoUnlimitsCoachConversion!=null) {
			eoUnlimitsCoachConversion.setRecordState(RecordStatus.DACTIVETED.getStatus());
			getRepository().save(eoUnlimitsCoachConversion);
		}
		return true;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<Order> buidOrders(Map<String, Object> sortOrders) {
		sortOrders.put(ORDER_BY, "coachDate");
		sortOrders.put(SORT_ORDER, "DESC");
		return super.buidOrders(sortOrders);
	}
	
	@Override
	public void postFetch(EOUnlimitsCoachConversion findObject, UIUnlimitsCoachConversion dtoObject) {
		
	}
	
	@Override
	public List<EOUnlimitsCoachConversion> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOUnlimitsCoachConversion> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOUnlimitsCoachConversion> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
	
	@Override
	public List<UIUnlimitsCoachConversion> findAllByCoachDateDateRange(String startDate, String endDate, MultiValueMap<String, String> headers, Map<String, Object> sortOrders, Map<String, Object> filters){
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		Date toStart = DateUtil.dateObject(startDate, UI_DATE_FORMAT_MM_DD_YY);
		Date toEnd = DateUtil.dateObject(endDate, UI_DATE_FORMAT_MM_DD_YY);
		return postFetch(coachLibararyRepository.findAllByCoachDateDateRange(eoCustBusinessApp.getId(), new java.sql.Date(toStart.getTime()), new java.sql.Date(toEnd.getTime()),  RecordStatus.ACTIVETED.getStatusIds()));
	}
	
	@Override
	public List<UIUnlimitsCoachConversion> findLastCoach() {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		List<EOUnlimitsCoachConversion> findTodayJournalLibarary = coachLibararyRepository.findTodayJournalLibarary(eoCustBusinessApp.getId(), RecordStatus.ACTIVETED.getStatusIds());
		if(CollectionUtils.isEmpty(findTodayJournalLibarary)) {
			return postFetch(coachLibararyRepository.findLastJournalLibarary(eoCustBusinessApp.getId(), RecordStatus.ACTIVETED.getStatusIds()));
		}
		return postFetch(findTodayJournalLibarary);
	}
	
	@Override
	public List<UIUnlimitsCoachConversion> findYesterdayCoach() {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return postFetch(coachLibararyRepository.findYesterdayJournalLibarary(eoCustBusinessApp.getId(), RecordStatus.ACTIVETED.getStatusIds()));
	}
}
