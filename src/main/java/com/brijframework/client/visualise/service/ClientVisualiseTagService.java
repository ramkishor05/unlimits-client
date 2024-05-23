/**
 * 
 */
package com.brijframework.client.visualise.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.visualise.entities.EOClientVisualiseTag;
import com.brijframework.client.visualise.model.UIClientVisualiseTag;

/**
 *  @author omnie
 */
public interface ClientVisualiseTagService extends CrudService<UIClientVisualiseTag, EOClientVisualiseTag, Long>{

	UIClientVisualiseTag getCurrent(Map<String, List<String>> headers);

}
