/**
 * 
 */
package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceUnlimitsExample;
import com.brijframework.client.entities.EOUnlimitsExample;

/**
 *  @author omnie
 */
public interface DeviceUnlimitsExampleService extends CrudService<UIDeviceUnlimitsExample, EOUnlimitsExample, Long>{

	UIDeviceUnlimitsExample getCurrent(Map<String, List<String>> headers);

}
