package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceUnlimitsVisualizeMapper;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.entities.EOUnlimits;
import com.brijframework.client.entities.EOUnlimitsVisualize;
import com.brijframework.client.exceptions.InvalidParameterException;
import com.brijframework.client.repository.UnlimitsRepository;
import com.brijframework.client.repository.UnlimitsVisualizeRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service("DeviceUnlimitsVisualizeService")
public class DeviceUnlimitVisualizeServiceImpl extends CrudServiceImpl<UIDeviceUnlimitsVisualize, EOUnlimitsVisualize, Long>
		implements DeviceUnlimitsVisualizeService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceUnlimitVisualizeServiceImpl.class);

	@Autowired
	protected UnlimitsVisualizeRepository unlimitsVisualizeRepository;

	@Autowired
	protected DeviceUnlimitsVisualizeMapper deviceUnlimitsVisualizeMapper;

	@Autowired
	protected UnlimitsRepository clientUnlimitsRepository;

	@Override
	public JpaRepository<EOUnlimitsVisualize, Long> getRepository() {
		return unlimitsVisualizeRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsVisualize, UIDeviceUnlimitsVisualize> getMapper() {
		return deviceUnlimitsVisualizeMapper;
	}
	

	{
		CustomPredicate<EOUnlimitsVisualize> unlimitsId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOUnlimits> subquery = criteriaQuery.subquery(EOUnlimits.class);
				Root<EOUnlimits> fromProject = subquery.from(EOUnlimits.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> imageLibararyPath = root.get("unlimits");
				In<Object> imageLibararyIn = criteriaBuilder.in(imageLibararyPath);
				imageLibararyIn.value(subquery);
				return imageLibararyIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for custBusinessApp: " + filter.getColumnValue(), e);
				return null;
			}
		};
		addCustomPredicate("unlimitsId", unlimitsId);
	}
	
	private void preSave(UIDeviceUnlimitsVisualize data, EOUnlimitsVisualize entity) {
		if(data.getEoUnlimits() instanceof EOUnlimits) {
			unlimitsVisualizeRepository.findOneByUnlimitsIdAndVisualizeYear(data.getUnlimitsId(), data.getVisualizeYear()).ifPresent(unlimitsVisualize->{
				entity.setId(unlimitsVisualize.getId());
				data.setId(unlimitsVisualize.getId());
			});
			entity.setUnlimits((EOUnlimits) data.getEoUnlimits());
		}
		if(data.getEoUnlimits() instanceof EOUnlimits) {
			unlimitsVisualizeRepository.findOneByUnlimitsIdAndVisualizeYear(data.getUnlimitsId(), data.getVisualizeYear()).ifPresent(unlimitsVisualize->{
				entity.setId(unlimitsVisualize.getId());
				data.setId(unlimitsVisualize.getId());
			});
			entity.setUnlimits((EOUnlimits) data.getEoUnlimits());
		}
		
		if(data.getEoUnlimits() instanceof EOUnlimits) {
			unlimitsVisualizeRepository.findOneByUnlimitsIdAndVisualizeYear(data.getUnlimitsId(), data.getVisualizeYear()).ifPresent(unlimitsVisualize->{
				entity.setId(unlimitsVisualize.getId());
				data.setId(unlimitsVisualize.getId());
			});
			entity.setUnlimits((EOUnlimits) data.getEoUnlimits());
		}
	}
	

	private void preSave(UIDeviceUnlimitsVisualize data) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		if(data.getUnlimitsType()!=null && data.getUnlimitsId()!=null) {
			switch (data.getUnlimitsType()) {
			case WORDS: {
				if(data.getEoUnlimits()==null) {
					data.setEoUnlimits(clientUnlimitsRepository.getReferenceById(data.getUnlimitsId()));
				}
				break;
			}
			case IMAGE: {
				if(data.getEoUnlimits()==null) {
					data.setEoUnlimits(clientUnlimitsRepository.getReferenceById(data.getUnlimitsId()));
				}
				break;
			}
			case EXAMPLE: {
				if(data.getEoUnlimits()==null) {
					data.setEoUnlimits(clientUnlimitsRepository.getReferenceById(data.getUnlimitsId()));
				}
				break;
			}
			default:
				throw new InvalidParameterException("Unexpected value: " + data.getType());
			}
		} else {
			throw new InvalidParameterException("Unexpected value: " + data.getType());
		}
	}
	
	@Override
	public void preAdd(UIDeviceUnlimitsVisualize data, Map<String, List<String>> headers) {
		preSave(data);
	}
	
	@Override
	public void preAdd(UIDeviceUnlimitsVisualize data, EOUnlimitsVisualize entity, Map<String, List<String>> headers) {
		preSave(data, entity);
	}

	@Override
	public void preUpdate(UIDeviceUnlimitsVisualize data, Map<String, List<String>> headers) {
		preSave(data);
	}

	@Override
	public void preUpdate(UIDeviceUnlimitsVisualize data, EOUnlimitsVisualize entity, Map<String, List<String>> headers) {
		preSave(data, entity);
	}

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.add("unlimits");
		ignoreProperties.add("unlimits");
		ignoreProperties.add("unlimits");
		return ignoreProperties;
	}

}
