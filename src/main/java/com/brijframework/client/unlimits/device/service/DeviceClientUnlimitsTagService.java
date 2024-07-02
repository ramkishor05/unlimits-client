/**
 * 
 */
package com.brijframework.client.unlimits.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOClientUnlimitsTag;
import com.brijframework.client.unlimits.model.UIClientUnlimitsTag;

/**
 *  @author omnie
 */
public interface DeviceClientUnlimitsTagService extends CrudService<UIClientUnlimitsTag, EOClientUnlimitsTag, Long>{

	UIClientUnlimitsTag getCurrent(Map<String, List<String>> headers);

}
