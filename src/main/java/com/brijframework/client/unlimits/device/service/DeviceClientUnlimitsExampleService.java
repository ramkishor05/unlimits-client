/**
 * 
 */
package com.brijframework.client.unlimits.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOClientUnlimitsExample;
import com.brijframework.client.unlimits.model.UIClientUnlimitsExample;

/**
 *  @author omnie
 */
public interface DeviceClientUnlimitsExampleService extends CrudService<UIClientUnlimitsExample, EOClientUnlimitsExample, Long>{

	UIClientUnlimitsExample getCurrent(Map<String, List<String>> headers);

}
