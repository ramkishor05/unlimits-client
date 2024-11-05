package com.brijframework.client.device.service;

import static com.brijframework.client.constants.Constants.COACH_DATE;
import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.INVALID_CLIENT;
import static com.brijframework.client.constants.Constants.UI_DATE_FORMAT_MM_DD_YY;
import static org.unlimits.rest.constants.RestConstant.ORDER_BY;
import static org.unlimits.rest.constants.RestConstant.SORT_ORDER;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceCoachChatMapper;
import com.brijframework.client.device.model.UIDeviceCoachChat;
import com.brijframework.client.entities.EOCoachChat;
import com.brijframework.client.entities.EOCoachSession;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.CoachConversionChatRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class DeviceCoachConversionChatServiceImpl 
extends CrudServiceImpl<UIDeviceCoachChat, EOCoachChat, Long> 
implements DeviceCoachChatService {
	
	private static final String COACH_SESSION_ID = "coachSessionId";

	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceCoachConversionChatServiceImpl.class);

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private CoachConversionChatRepository coachConversionChatRepository;
	
	@Autowired
	private DeviceCoachChatMapper coachConversionChatMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOCoachChat, Long> getRepository() {
		return coachConversionChatRepository;
	}

	@Override
	public GenericMapper<EOCoachChat, UIDeviceCoachChat> getMapper() {
		return coachConversionChatMapper;
	}
	
	{
		CustomPredicate<EOCoachChat> coachDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
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
		
		CustomPredicate<EOCoachChat> coachSessionId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOCoachSession> subquery = criteriaQuery.subquery(EOCoachSession.class);
				Root<EOCoachSession> fromProject = subquery.from(EOCoachSession.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> coachSessionPath = root.get("coachSession");
				In<Object> coachSessionIn = criteriaBuilder.in(coachSessionPath);
				coachSessionIn.value(subquery);
				return coachSessionIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception in coachDate: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		addCustomPredicate(COACH_DATE, coachDate);
		
		addCustomPredicate(COACH_SESSION_ID, coachSessionId);
		
	}

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		ignoreProperties.add(CUST_BUSINESS_APP);
		return ignoreProperties;
	}
	
	@Override
	public void preAdd(UIDeviceCoachChat data, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preAdd(UIDeviceCoachChat data, EOCoachChat entity,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setRecordState(RecordStatus.ACTIVETED.getStatus());
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIDeviceCoachChat data, EOCoachChat entity,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setRecordState(RecordStatus.ACTIVETED.getStatus());
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public List<UIDeviceCoachChat> postFetch(List<EOCoachChat> findObjects, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		List<UIDeviceCoachChat> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getCoachDate().compareTo(op2.getCoachDate()));
		return uiObjects;
	}
	
	@Override
	public Boolean deleteById(Long uuid) {
		EOCoachChat eoCoachConversion = find(uuid);
		if(eoCoachConversion!=null) {
			eoCoachConversion.setRecordState(RecordStatus.DACTIVETED.getStatus());
			getRepository().save(eoCoachConversion);
		}
		return true;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<Order> buidOrders(Map<String, Object> sortOrders) {
		sortOrders.put(ORDER_BY, "coachDate");
		sortOrders.put(SORT_ORDER, "ASC");
		return super.buidOrders(sortOrders);
	}

	@Override
	public List<EOCoachChat> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOCoachChat> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOCoachChat> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return super.repositoryFindAll(headers, sort, filters);
	}
	
	@Override
	public List<UIDeviceCoachChat> findAllByCoachDateDateRange(Long sessionId,String startDate, String endDate, 
			Map<String, List<String>> headers, Map<String, Object> filters , Map<String, Object> sortOrders){
		Date toStart = DateUtil.dateObject(startDate, UI_DATE_FORMAT_MM_DD_YY);
		Date toEnd = DateUtil.dateObject(endDate, UI_DATE_FORMAT_MM_DD_YY);
		return postFetch(coachConversionChatRepository.findAllByCoachDateDateRange(sessionId, new java.sql.Date(toStart.getTime()), new java.sql.Date(toEnd.getTime()),  RecordStatus.ACTIVETED.getStatusIds()));
	}
	
	@Override
	public UIDeviceCoachChat findFirstChat(Long sessionId, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		Optional<EOCoachChat> findLastChat = coachConversionChatRepository.findFirstChat(sessionId, RecordStatus.ACTIVETED.getStatusIds());
		EOCoachChat eoCoachConversionChat = findLastChat.orElse(null);
		if(eoCoachConversionChat==null) {
			return null;
		}
		UIDeviceCoachChat uiDeviceCoachConversionChat = coachConversionChatMapper.mapToDTO(eoCoachConversionChat);
		postFetch(  eoCoachConversionChat, uiDeviceCoachConversionChat,  headers);
		return uiDeviceCoachConversionChat;
	}
	
	@Override
	public UIDeviceCoachChat findLastChat(Long sessionId,  Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		Optional<EOCoachChat> findLastChat = coachConversionChatRepository.findLastChat(sessionId, RecordStatus.ACTIVETED.getStatusIds());
		EOCoachChat eoCoachConversionChat = findLastChat.orElse(null);
		if(eoCoachConversionChat==null) {
			return null;
		}
		UIDeviceCoachChat uiDeviceCoachConversionChat = coachConversionChatMapper.mapToDTO(eoCoachConversionChat);
		postFetch(  eoCoachConversionChat,uiDeviceCoachConversionChat,  headers);
		return uiDeviceCoachConversionChat;
	}
}
