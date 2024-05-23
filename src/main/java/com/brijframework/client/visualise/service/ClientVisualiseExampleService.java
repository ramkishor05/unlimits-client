/**
 * 
 */
package com.brijframework.client.visualise.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.visualise.entities.EOClientVisualiseExample;
import com.brijframework.client.visualise.model.UIClientVisualiseExample;

/**
 *  @author omnie
 */
public interface ClientVisualiseExampleService extends CrudService<UIClientVisualiseExample, EOClientVisualiseExample, Long>{

	UIClientVisualiseExample getCurrent(Map<String, List<String>> headers);

}
