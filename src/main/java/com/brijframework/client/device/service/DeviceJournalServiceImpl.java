/**
 * 
 */
package com.brijframework.client.device.service;
import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.UI_DATE_FORMAT_MM_DD_YY;
import static com.brijframework.client.constants.Constants.INVALID_CLIENT;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.brijframework.util.reflect.FieldUtil;
import org.brijframework.util.support.ReflectionAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceJournalMapper;
import com.brijframework.client.device.model.UIDeviceJournalItem;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;
import com.brijframework.client.entities.EOJournal;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.JournalRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceJournalServiceImpl extends CrudServiceImpl<UIDeviceJournalItem, EOJournal, Long>
		implements DeviceJournalService {
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceJournalServiceImpl.class);

	private static final String JOURNAL_DATE = "journalDate";

	@Autowired
	private JournalRepository clientJournalRepository;

	@Autowired
	private DeviceJournalMapper clientJournalMapper;

	@Override
	public JpaRepository<EOJournal, Long> getRepository() {
		return clientJournalRepository;
	}

	@Override
	public GenericMapper<EOJournal, UIDeviceJournalItem> getMapper() {
		return clientJournalMapper;
	}
	
	{
		CustomPredicate<EOJournal> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
				In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
				custBusinessAppIn.value(filter.getColumnValue());
				return custBusinessAppIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		CustomPredicate<EOJournal> journalDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Date> journalDatePath = root.get(JOURNAL_DATE);
				In<Object> journalDatepIn = criteriaBuilder.in(journalDatePath);
				DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MM_DD_YY);
				Date date = timeFormat.parse(filter.getColumnValue().toString());
				journalDatepIn.value(new java.sql.Date(date.getTime()) );
				return journalDatepIn;
			} catch (ParseException e) {
				LOGGER.error("WARN: unexpected parse exception in journalDate: " + filter.getColumnValue(), e);
				return null;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception in journalDate: " + filter.getColumnValue(), e);
				return null;
			}
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(JOURNAL_DATE, journalDate);
	}
	

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		ignoreProperties.add(CUST_BUSINESS_APP);
		return ignoreProperties;
	}
	
	@Override
	public void preAdd(UIDeviceJournalItem data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void preAdd(UIDeviceJournalItem data, EOJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		List<EOJournal> findJournalList = clientJournalRepository.findAllByCustBusinessAppAndJournalId(eoCustBusinessApp, entity.getJournalId());
		if(!CollectionUtils.isEmpty(findJournalList)) {
			entity.setId(findJournalList.get(0).getId());
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIDeviceJournalItem data, EOJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		List<EOJournal> findJournalList = clientJournalRepository.findAllByCustBusinessAppAndJournalId(eoCustBusinessApp, entity.getJournalId());
		if(!CollectionUtils.isEmpty(findJournalList)) {
			entity.setId(findJournalList.get(0).getId());
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public List<EOJournal> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOJournal> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOJournal> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
