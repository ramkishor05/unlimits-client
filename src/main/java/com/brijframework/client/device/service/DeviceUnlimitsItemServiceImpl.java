/**
 * 
 */
package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.brijframework.util.reflect.FieldUtil;
import org.brijframework.util.support.ReflectionAccess;
import org.brijframework.util.text.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.beans.PageDetail;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.client.constants.Constants;
import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceUnlimitsItemMapper;
import com.brijframework.client.device.model.UIDeviceUnlimitsItem;
import com.brijframework.client.entities.EOEntityObject;
import com.brijframework.client.entities.EOUnlimits;
import com.brijframework.client.entities.EOUnlimitsItem;
import com.brijframework.client.forgin.model.FileContent;
import com.brijframework.client.forgin.repository.PexelClient;
import com.brijframework.client.repository.UnlimitsItemRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

/**
 * @author omnie
 */
@Service
public class DeviceUnlimitsItemServiceImpl extends CrudServiceImpl<UIDeviceUnlimitsItem, EOUnlimitsItem, Long>
		implements DeviceUnlimitsItemService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceUnlimitsItemServiceImpl.class);

	@Autowired
	private UnlimitsItemRepository clientUnlimitsItemRepository;

	@Autowired
	private DeviceUnlimitsItemMapper clientUnlimitsItemMapper;
	
	@Autowired
	private PexelClient pexelClient;

	@Override
	public JpaRepository<EOUnlimitsItem, Long> getRepository() {
		return clientUnlimitsItemRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsItem, UIDeviceUnlimitsItem> getMapper() {
		return clientUnlimitsItemMapper;
	}
	
	{
		CustomPredicate<EOUnlimitsItem> unlimitsId = (type, root, criteriaQuery, criteriaBuilder,
				filter) -> {
			try {
				Subquery<EOUnlimits> subquery = criteriaQuery.subquery(EOUnlimits.class);
				Root<EOUnlimits> fromProject = subquery.from(EOUnlimits.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> unlimitsPath = root.get("unlimits");
				In<Object> unlimitsIn = criteriaBuilder.in(unlimitsPath);
				unlimitsIn.value(subquery);
				return unlimitsIn;
			} catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for subCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};

		addCustomPredicate("unlimitsId", unlimitsId);
	}
		
	
	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		LOGGER.info("ignoreProperties : {}", ignoreProperties);
		return ignoreProperties;
	}
	
	@Override
	public void preAdd(UIDeviceUnlimitsItem data, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		saveDetail(data);
	}

	private void saveDetail(UIDeviceUnlimitsItem data) {
		if(StringUtil.isEmpty(data.getId()) && StringUtil.isNonEmpty(data.getTagName()) && StringUtil.isEmpty(data.getImageId()) && StringUtil.isEmpty(data.getImageUrl())) {
			PageDetail<FileContent> pageDetail = pexelClient.getPageDetail(data.getTagName(), 0, 1);
			if(pageDetail!=null && !CollectionUtils.isEmpty(pageDetail.getElements())) {
				List<FileContent> elements = pageDetail.getElements();
				FileContent fileContent = elements.get(0);
				data.setImageUrl(fileContent.getUrl());
			}
		}
	}

	@Override
	public void preAdd(UIDeviceUnlimitsItem data, EOUnlimitsItem entity, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		saveDetail(data);
	}

	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		filters.put(Constants.RECORD_STATE, RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void deleteByUnlimitsId(Long id) {
		clientUnlimitsItemRepository.deleteByUnlimitsId(id);
	}

}
