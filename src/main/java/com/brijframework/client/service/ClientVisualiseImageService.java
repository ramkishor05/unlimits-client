/**
 * 
 */
package com.brijframework.client.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.visualise.EOClientVisualiseImage;
import com.brijframework.client.model.visualise.UIClientVisualiseImage;

/**
 *  @author omnie
 */
public interface ClientVisualiseImageService extends CrudService<UIClientVisualiseImage, EOClientVisualiseImage, Long>{

	UIClientVisualiseImage getCurrent(Map<String, List<String>> headers);

}
