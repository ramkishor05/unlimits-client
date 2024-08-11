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
import com.brijframework.client.entities.EOUnlimitsImage;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.global.mapper.GlobalUnlimitsImageMapper;
import com.brijframework.client.global.model.UIGlobalUnlimitsImage;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.repository.UnlimitsImageRepository;

/**
 * @author omnie
 */
@Service
public class GlobalUnlimitsImageServiceImpl extends CrudServiceImpl<UIGlobalUnlimitsImage, EOUnlimitsImage, Long>
		implements GlobalUnlimitsImageService {

	@Autowired
	private UnlimitsImageRepository unlimitsImageRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private GlobalUnlimitsImageMapper globalUnlimitsImageMapper;

	@Override
	public JpaRepository<EOUnlimitsImage, Long> getRepository() {
		return unlimitsImageRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsImage, UIGlobalUnlimitsImage> getMapper() {
		return globalUnlimitsImageMapper;
	}

	@Override
	public void preAdd(UIGlobalUnlimitsImage data, EOUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = unlimitsImageRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getImageItems().forEach(tagItem->tagItem.setUnlimitsImage(entity));
	}
	
	@Override
	public void postAdd(UIGlobalUnlimitsImage data, EOUnlimitsImage entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIGlobalUnlimitsImage data, EOUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		if(StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = unlimitsImageRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS+maxTransactionId);
			entity.setName(MY_UNLIMITS+maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
		entity.getImageItems().forEach(tagItem->tagItem.setUnlimitsImage(entity));
	}
	
	@Override
	public void postUpdate(UIGlobalUnlimitsImage data, EOUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public UIGlobalUnlimitsImage getCurrent( Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return globalUnlimitsImageMapper.mapToDTO(eoCustBusinessApp.getUnlimitsImage());
	}
	
}
