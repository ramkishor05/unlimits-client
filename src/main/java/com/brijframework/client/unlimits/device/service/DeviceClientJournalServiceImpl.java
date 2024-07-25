/**
 * 
 */
package com.brijframework.client.unlimits.device.service;
import static com.brijframework.client.constants.ClientConstants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.ClientConstants.INVALID_CLIENT;
import static com.brijframework.client.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientJournalMapper;
import com.brijframework.client.repository.ClientJournalRepository;
import com.brijframework.client.unlimits.entities.EOCustJournal;
import com.brijframework.client.unlimits.model.UICustJournalItem;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceClientJournalServiceImpl extends CrudServiceImpl<UICustJournalItem, EOCustJournal, Long>
		implements DeviceClientJournalService {

	private static final String JOURNAL_DATE = "journalDate";

	@Autowired
	private ClientJournalRepository clientJournalRepository;

	@Autowired
	private ClientJournalMapper clientJournalMapper;

	@Override
	public JpaRepository<EOCustJournal, Long> getRepository() {
		return clientJournalRepository;
	}

	@Override
	public GenericMapper<EOCustJournal, UICustJournalItem> getMapper() {
		return clientJournalMapper;
	}
	
	{
		CustomPredicate<EOCustJournal> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOCustJournal> journalDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> journalDatePath = root.get(JOURNAL_DATE);
			In<Object> journalDatepIn = criteriaBuilder.in(journalDatePath);
			DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY);
			Date date = null;
			try {
				date = timeFormat.parse(filter.getColumnValue().toString());
			} catch (ParseException e) {
				System.err.println("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue());
			}
			journalDatepIn.value(new java.sql.Date(date.getTime()) );
			return journalDatepIn;
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(JOURNAL_DATE, journalDate);
	}

	@Override
	public void preAdd(UICustJournalItem data, EOCustJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		List<EOCustJournal> findClientJournalList = clientJournalRepository.findAllByCustBusinessAppAndJournalId(eoCustBusinessApp, entity.getJournalId());
		if(!CollectionUtils.isEmpty(findClientJournalList)) {
			entity.setId(findClientJournalList.get(0).getId());
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UICustJournalItem data, EOCustJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		List<EOCustJournal> findClientJournalList = clientJournalRepository.findAllByCustBusinessAppAndJournalId(eoCustBusinessApp, entity.getJournalId());
		if(!CollectionUtils.isEmpty(findClientJournalList)) {
			entity.setId(findClientJournalList.get(0).getId());
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public List<EOCustJournal> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOCustJournal> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOCustJournal> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
