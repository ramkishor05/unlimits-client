/**
 * 
 */
package com.brijframework.client.unlimits.global.service;

import static com.brijframework.client.constants.ClientConstants.MY_UNLIMITS;

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
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.CustUnlimitsTagMapper;
import com.brijframework.client.repository.CustUnlimitsTagRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsTag;
import com.brijframework.client.unlimits.model.UICustUnlimitsTag;

/**
 * @author omnie
 */
@Service
public class GlobalUnlimitsTagServiceImpl extends CrudServiceImpl<UICustUnlimitsTag, EOCustUnlimitsTag, Long>
		implements GlobalCustUnlimitsTagService {

	@Autowired
	private CustUnlimitsTagRepository custUnlimitsTagRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private CustUnlimitsTagMapper clientUnlimitsTagMapper;

	@Override
	public JpaRepository<EOCustUnlimitsTag, Long> getRepository() {
		return custUnlimitsTagRepository;
	}

	@Override
	public GenericMapper<EOCustUnlimitsTag, UICustUnlimitsTag> getMapper() {
		return clientUnlimitsTagMapper;
	}

	@Override
	public void preAdd(UICustUnlimitsTag data, EOCustUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = custUnlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getTagItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
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
			int maxTransactionId = custUnlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getTagItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
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
	public UICustUnlimitsTag getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsTagMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsTag());
	}
	
}
