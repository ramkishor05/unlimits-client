package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceCoachGroupMapper;
import com.brijframework.client.device.model.UIDeviceCoachGroup;
import com.brijframework.client.entities.EOCoachGroup;
import com.brijframework.client.repository.CoachLibararyRepository;

@Service
public class DeviceCoachServiceImpl extends CrudServiceImpl<UIDeviceCoachGroup, EOCoachGroup, Long> implements DeviceCoachService {
	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private CoachLibararyRepository coachLibararyRepository;
	
	@Autowired
	private DeviceCoachGroupMapper deviceCoachLibararyMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOCoachGroup, Long> getRepository() {
		return coachLibararyRepository;
	}

	@Override
	public GenericMapper<EOCoachGroup, UIDeviceCoachGroup> getMapper() {
		return deviceCoachLibararyMapper;
	}
	
	@Override
	public List<UIDeviceCoachGroup> postFetch(List<EOCoachGroup> findObjects) {
		List<UIDeviceCoachGroup> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public void postFetch(EOCoachGroup findObject, UIDeviceCoachGroup dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
	}
}
