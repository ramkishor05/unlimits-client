/**
 * 
 */
package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceUnlimitsImage;
import com.brijframework.client.entities.EOUnlimitsImage;

/**
 *  @author omnie
 */
public interface DeviceUnlimitsImageService extends CrudService<UIDeviceUnlimitsImage, EOUnlimitsImage, Long>{

	UIDeviceUnlimitsImage getCurrent(Map<String, List<String>> headers);

}
