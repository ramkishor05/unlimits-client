/**
 * 
 */
package com.brijframework.client.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.visualise.EOClientVisualiseExample;
import com.brijframework.client.model.visualise.UIClientVisualiseExample;

/**
 *  @author omnie
 */
public interface ClientVisualiseExampleService extends CrudService<UIClientVisualiseExample, EOClientVisualiseExample, Long>{

	UIClientVisualiseExample getCurrent(Map<String, List<String>> headers);

}
