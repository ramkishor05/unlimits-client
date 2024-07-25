/**
 * 
 */
package com.brijframework.client.unlimits.global.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOCustUnlimitsImage;
import com.brijframework.client.unlimits.model.UICustUnlimitsImage;

/**
 *  @author omnie
 */
public interface GlobalCustUnlimitsImageService extends CrudService<UICustUnlimitsImage, EOCustUnlimitsImage, Long>{

	UICustUnlimitsImage getCurrent(Map<String, List<String>> headers);

}
