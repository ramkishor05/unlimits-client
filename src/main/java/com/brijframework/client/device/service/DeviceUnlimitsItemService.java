/**
 * 
 */
package com.brijframework.client.device.service;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceUnlimitsItem;
import com.brijframework.client.entities.EOUnlimitsItem;

/**
 *  @author omnie
 */
public interface DeviceUnlimitsItemService extends CrudService<UIDeviceUnlimitsItem, EOUnlimitsItem, Long>{

	void deleteByUnlimitsId(Long id);

}
