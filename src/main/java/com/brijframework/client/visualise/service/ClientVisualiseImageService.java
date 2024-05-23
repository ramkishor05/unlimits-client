/**
 * 
 */
package com.brijframework.client.visualise.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.visualise.entities.EOClientVisualiseImage;
import com.brijframework.client.visualise.model.UIClientVisualiseImage;

/**
 *  @author omnie
 */
public interface ClientVisualiseImageService extends CrudService<UIClientVisualiseImage, EOClientVisualiseImage, Long>{

	UIClientVisualiseImage getCurrent(Map<String, List<String>> headers);

}
