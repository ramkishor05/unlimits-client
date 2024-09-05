/**
 * 
 */
package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceUnlimitsTag;
import com.brijframework.client.entities.EOUnlimitsTag;

/**
 *  @author omnie
 */
public interface DeviceUnlimitsTagService extends CrudService<UIDeviceUnlimitsTag, EOUnlimitsTag, Long>{

	UIDeviceUnlimitsTag getCurrent(Map<String, List<String>> headers);

	
}
