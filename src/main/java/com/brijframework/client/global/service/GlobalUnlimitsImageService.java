/**
 * 
 */
package com.brijframework.client.global.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.EOUnlimitsImage;
import com.brijframework.client.global.model.UIGlobalUnlimitsImage;

/**
 *  @author omnie
 */
public interface GlobalUnlimitsImageService extends CrudService<UIGlobalUnlimitsImage, EOUnlimitsImage, Long>{

	UIGlobalUnlimitsImage getCurrent(Map<String, List<String>> headers);

}
