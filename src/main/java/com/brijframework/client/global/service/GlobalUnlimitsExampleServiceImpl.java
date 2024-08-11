/**
 * 
 */
package com.brijframework.client.global.service;
import static com.brijframework.client.constants.Constants.MY_UNLIMITS;

import java.util.List;
import java.util.Map;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOUnlimitsExample;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.global.mapper.GlobalUnlimitsExampleMapper;
import com.brijframework.client.global.model.UIGlobalUnlimitsExample;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.repository.UnlimitsExampleRepository;

/**
 * @author omnie
 */
@Service
public class GlobalUnlimitsExampleServiceImpl extends CrudServiceImpl<UIGlobalUnlimitsExample, EOUnlimitsExample, Long>
		implements GlobalUnlimitsExampleService {

	@Autowired
	private UnlimitsExampleRepository unlimitsExampleRepository;

	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;
	
	@Autowired
	private GlobalUnlimitsExampleMapper globalUnlimitsExampleMapper;

	@Override
	public JpaRepository<EOUnlimitsExample, Long> getRepository() {
		return unlimitsExampleRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsExample, UIGlobalUnlimitsExample> getMapper() {
		return globalUnlimitsExampleMapper;
	}

	@Override
	public void preAdd(UIGlobalUnlimitsExample data, EOUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = unlimitsExampleRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postAdd(UIGlobalUnlimitsExample data, EOUnlimitsExample entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setUnlimitsExample(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIGlobalUnlimitsExample data, EOUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = unlimitsExampleRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void postUpdate(UIGlobalUnlimitsExample data, EOUnlimitsExample entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setUnlimitsExample(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public UIGlobalUnlimitsExample getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return globalUnlimitsExampleMapper.mapToDTO(eoCustBusinessApp.getUnlimitsExample());
	}
	
}
