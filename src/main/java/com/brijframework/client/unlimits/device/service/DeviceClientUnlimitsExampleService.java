/**
 * 
 */
package com.brijframework.client.unlimits.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOCustUnlimitsExample;
import com.brijframework.client.unlimits.model.UICustUnlimitsExample;

/**
 *  @author omnie
 */
public interface DeviceClientUnlimitsExampleService extends CrudService<UICustUnlimitsExample, EOCustUnlimitsExample, Long>{

	UICustUnlimitsExample getCurrent(Map<String, List<String>> headers);

}
