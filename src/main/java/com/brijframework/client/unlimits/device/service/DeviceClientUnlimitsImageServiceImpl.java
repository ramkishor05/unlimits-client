/**
 * 
 */
package com.brijframework.client.unlimits.device.service;
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
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientUnlimitsImageMapper;
import com.brijframework.client.repository.ClientUnlimitsImageRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsImage;
import com.brijframework.client.unlimits.model.UIClientUnlimitsImage;

/**
 * @author omnie
 */
@Service
public class DeviceClientUnlimitsImageServiceImpl extends CrudServiceImpl<UIClientUnlimitsImage, EOClientUnlimitsImage, Long>
		implements DeviceClientUnlimitsImageService {

	@Autowired
	private ClientUnlimitsImageRepository clientUnlimitsImageRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private ClientUnlimitsImageMapper clientUnlimitsImageMapper;

	@Override
	public JpaRepository<EOClientUnlimitsImage, Long> getRepository() {
		return clientUnlimitsImageRepository;
	}

	@Override
	public GenericMapper<EOClientUnlimitsImage, UIClientUnlimitsImage> getMapper() {
		return clientUnlimitsImageMapper;
	}

	@Override
	protected void preAdd(UIClientUnlimitsImage data, EOClientUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsImageRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getImageItems().forEach(tagItem->tagItem.setUnlimitsImage(entity));
	}
	
	@Override
	protected void postAdd(UIClientUnlimitsImage data, EOClientUnlimitsImage entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	protected void preUpdate(UIClientUnlimitsImage data, EOClientUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsImageRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getImageItems().forEach(tagItem->tagItem.setUnlimitsImage(entity));
	}
	
	@Override
	protected void postUpdate(UIClientUnlimitsImage data, EOClientUnlimitsImage entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public UIClientUnlimitsImage getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsImageMapper.mapToDTO(eoCustBusinessApp.getClientUnlimitsImage());
	}
	
	@Override
	protected List<EOClientUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsImageRepository.findAllByCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	protected Page<EOClientUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsImageRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}
	
	@Override
	protected List<EOClientUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Sort sort) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsImageRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}
}