/**
 * 
 */
package com.brijframework.client.global.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.EOUnlimitsExample;
import com.brijframework.client.global.model.UIGlobalUnlimitsExample;

/**
 *  @author omnie
 */
public interface GlobalUnlimitsExampleService extends CrudService<UIGlobalUnlimitsExample, EOUnlimitsExample, Long>{

	UIGlobalUnlimitsExample getCurrent(Map<String, List<String>> headers);

}
