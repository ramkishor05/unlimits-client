/**
 * 
 */
package com.brijframework.client.unlimits.global.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOClientUnlimitsImage;
import com.brijframework.client.unlimits.model.UIClientUnlimitsImage;

/**
 *  @author omnie
 */
public interface GlobalClientUnlimitsImageService extends CrudService<UIClientUnlimitsImage, EOClientUnlimitsImage, Long>{

	UIClientUnlimitsImage getCurrent(Map<String, List<String>> headers);

}
