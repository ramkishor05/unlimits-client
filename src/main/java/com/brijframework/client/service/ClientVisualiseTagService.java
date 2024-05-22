/**
 * 
 */
package com.brijframework.client.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.visualise.EOClientVisualiseTag;
import com.brijframework.client.model.visualise.UIClientVisualiseTag;

/**
 *  @author omnie
 */
public interface ClientVisualiseTagService extends CrudService<UIClientVisualiseTag, EOClientVisualiseTag, Long>{

	UIClientVisualiseTag getCurrent(Map<String, List<String>> headers);

}
