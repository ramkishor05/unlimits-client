/**
 * 
 */
package com.brijframework.client.unlimits.device.service;

import static com.brijframework.client.constants.ClientConstants.CUST_APP_ID;
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
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientUnlimitsTagMapper;
import com.brijframework.client.repository.ClientUnlimitsTagRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsTag;
import com.brijframework.client.unlimits.model.UIClientUnlimitsTag;

/**
 * @author omnie
 */
@Service
public class DeviceClientUnlimitsTagServiceImpl extends CrudServiceImpl<UIClientUnlimitsTag, EOClientUnlimitsTag, Long>
		implements DeviceClientUnlimitsTagService {

	@Autowired
	private ClientUnlimitsTagRepository clientUnlimitsTagRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private ClientUnlimitsTagMapper clientUnlimitsTagMapper;

	@Override
	public JpaRepository<EOClientUnlimitsTag, Long> getRepository() {
		return clientUnlimitsTagRepository;
	}

	@Override
	public GenericMapper<EOClientUnlimitsTag, UIClientUnlimitsTag> getMapper() {
		return clientUnlimitsTagMapper;
	}

	@Override
	protected void preAdd(UIClientUnlimitsTag data, EOClientUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getTagItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
	}
	
	@Override
	protected void postAdd(UIClientUnlimitsTag data, EOClientUnlimitsTag entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsTag(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	protected void preUpdate(UIClientUnlimitsTag data, EOClientUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getTagItems().forEach(tagItem->tagItem.setUnlimitsTag(entity));
	}
	
	@Override
	protected void postUpdate(UIClientUnlimitsTag data, EOClientUnlimitsTag entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsTag(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public UIClientUnlimitsTag getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsTagMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsTag());
	}
	
	@Override
	protected List<EOClientUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientUnlimitsTagRepository.findAllByCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	protected Page<EOClientUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsTagRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}
	
	@Override
	protected List<EOClientUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers, Sort sort) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsTagRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}
}
