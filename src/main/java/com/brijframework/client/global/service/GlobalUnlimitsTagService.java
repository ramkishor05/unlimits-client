/**
 * 
 */
package com.brijframework.client.global.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.EOUnlimitsTag;
import com.brijframework.client.global.model.UIGlobalUnlimitsTag;

/**
 *  @author omnie
 */
public interface GlobalUnlimitsTagService extends CrudService<UIGlobalUnlimitsTag, EOUnlimitsTag, Long>{

	UIGlobalUnlimitsTag getCurrent(Map<String, List<String>> headers);

}
