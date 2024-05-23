/**
 * 
 */
package com.brijframework.client.visualise.service;

import static com.brijframework.client.constants.ClientConstants.CUST_APP_ID;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientVisualiseImageMapper;
import com.brijframework.client.repository.ClientVisualiseImageRepository;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.visualise.entities.EOClientVisualiseImage;
import com.brijframework.client.visualise.model.UIClientVisualiseImage;

/**
 * @author omnie
 */
@Service
public class ClientVisualiseImageServiceImpl extends CrudServiceImpl<UIClientVisualiseImage, EOClientVisualiseImage, Long>
		implements ClientVisualiseImageService {

	@Autowired
	private ClientVisualiseImageRepository clientVisualiseImageRepository;
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private ClientVisualiseImageMapper clientVisualiseImageMapper;

	@Override
	public JpaRepository<EOClientVisualiseImage, Long> getRepository() {
		return clientVisualiseImageRepository;
	}

	@Override
	public GenericMapper<EOClientVisualiseImage, UIClientVisualiseImage> getMapper() {
		return clientVisualiseImageMapper;
	}

	@Override
	protected void preAdd(UIClientVisualiseImage data, EOClientVisualiseImage entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		entity.getCustBusinessApp().setClientVisualiseImage(entity);
		entity.getImageItems().forEach(tagItem->tagItem.setVisualiseImage(entity));
	}
	
	@Override
	protected void preUpdate(UIClientVisualiseImage data, EOClientVisualiseImage entity, Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0))));
		entity.getImageItems().forEach(tagItem->tagItem.setVisualiseImage(entity));
	}
	
	@Override
	public UIClientVisualiseImage getCurrent( Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientVisualiseImageMapper.mapToDTO(eoCustBusinessApp.getClientVisualiseImage());
	}
	
	@Override
	protected List<EOClientVisualiseImage> repositoryFindAll(Map<String, List<String>> headers) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientVisualiseImageRepository.findAllByCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	protected Page<EOClientVisualiseImage> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientVisualiseImageRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}
	
	@Override
	protected List<EOClientVisualiseImage> repositoryFindAll(Map<String, List<String>> headers, Sort sort) {
		List<String> list = headers.get(CUST_APP_ID);
		if(CollectionUtils.isEmpty(list)) {
			throw new UserNotFoundException("Invalid client");
		}
		EOCustBusinessApp eoCustBusinessApp = custBusinessAppRepository.getReferenceById(Long.valueOf(list.get(0)));
		return clientVisualiseImageRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}
}
