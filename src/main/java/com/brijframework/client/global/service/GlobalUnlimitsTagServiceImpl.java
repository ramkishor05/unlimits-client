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
import com.brijframework.client.entities.EOUnlimitsTag;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.global.mapper.GlobalUnlimitsTagMapper;
import com.brijframework.client.global.model.UIGlobalUnlimitsTag;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.repository.UnlimitsTagRepository;

/**
 * @author omnie
 */
@Service
public class GlobalUnlimitsTagServiceImpl extends CrudServiceImpl<UIGlobalUnlimitsTag, EOUnlimitsTag, Long>
		implements GlobalUnlimitsTagService {

	@Autowired
	private UnlimitsTagRepository unlimitsTagRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private GlobalUnlimitsTagMapper globalUnlimitsTagMapper;

	@Override
	public JpaRepository<EOUnlimitsTag, Long> getRepository() {
		return unlimitsTagRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsTag, UIGlobalUnlimitsTag> getMapper() {
		return globalUnlimitsTagMapper;
	}

	@Override
	public void preAdd(UIGlobalUnlimitsTag data, EOUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = unlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getTagItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
	}
	
	@Override
	public void postAdd(UIGlobalUnlimitsTag data, EOUnlimitsTag entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setUnlimitsTag(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIGlobalUnlimitsTag data, EOUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = unlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getTagItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
	}
	
	@Override
	public void postUpdate(UIGlobalUnlimitsTag data, EOUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setUnlimitsTag(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public UIGlobalUnlimitsTag getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return globalUnlimitsTagMapper.mapToDTO(eoCustBusinessApp.getUnlimitsTag());
	}
	
}
