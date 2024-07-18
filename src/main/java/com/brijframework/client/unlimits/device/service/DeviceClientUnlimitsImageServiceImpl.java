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
import com.brijframework.client.mapper.ClientUnlimitsImageItemMapper;
import com.brijframework.client.mapper.ClientUnlimitsImageMapper;
import com.brijframework.client.repository.ClientUnlimitsImageItemRepository;
import com.brijframework.client.repository.ClientUnlimitsImageRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsImage;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsImageItem;
import com.brijframework.client.unlimits.model.UIClientUnlimitsImage;

/**
 * @author omnie
 */
@Service
public class DeviceClientUnlimitsImageServiceImpl extends CrudServiceImpl<UIClientUnlimitsImage, EOClientUnlimitsImage, Long>
		implements DeviceClientUnlimitsImageService {

	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;


	@Autowired
	private ClientUnlimitsImageRepository clientUnlimitsImageRepository;
	
	@Autowired
	private ClientUnlimitsImageMapper clientUnlimitsImageMapper;
	

	@Autowired
	private ClientUnlimitsImageItemRepository clientUnlimitsImageItemRepository;
	

	@Autowired
	private ClientUnlimitsImageItemMapper clientUnlimitsImageItemMapper;

	@Override
	public JpaRepository<EOClientUnlimitsImage, Long> getRepository() {
		return clientUnlimitsImageRepository;
	}

	@Override
	public GenericMapper<EOClientUnlimitsImage, UIClientUnlimitsImage> getMapper() {
		return clientUnlimitsImageMapper;
	}

	@Override
	public void preAdd(UIClientUnlimitsImage data, EOClientUnlimitsImage entity, Map<String, List<String>> headers) {
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
	}
	
	@Override
	public void postAdd(UIClientUnlimitsImage data, EOClientUnlimitsImage entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIClientUnlimitsImage data, EOClientUnlimitsImage entity, Map<String, List<String>> headers) {
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
	}
	
	@Override
	public void postUpdate(UIClientUnlimitsImage data, EOClientUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setClientUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UIClientUnlimitsImage dtoObject, EOClientUnlimitsImage entityObject,
			UIClientUnlimitsImage updateDtoObject, EOClientUnlimitsImage updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientUnlimitsImageItem> imageItems = clientUnlimitsImageItemMapper.mapToDAO(dtoObject.getImageItems());
		imageItems.forEach(item->item.setUnlimitsImage(updateEntityObject));
		List<EOClientUnlimitsImageItem> imageItemsReturn = clientUnlimitsImageItemRepository.saveAll(imageItems);
		updateDtoObject.setImageItems(clientUnlimitsImageItemMapper.mapToDTO(imageItemsReturn));
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
	public List<EOClientUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsImageRepository.findAllByCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public Page<EOClientUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsImageRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}
	
	@Override
	public List<EOClientUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if(eoCustBusinessApp==null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsImageRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}
}
