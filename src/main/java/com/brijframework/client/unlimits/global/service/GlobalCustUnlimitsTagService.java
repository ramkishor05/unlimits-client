/**
 * 
 */
package com.brijframework.client.unlimits.global.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOCustUnlimitsTag;
import com.brijframework.client.unlimits.model.UICustUnlimitsTag;

/**
 *  @author omnie
 */
public interface GlobalCustUnlimitsTagService extends CrudService<UICustUnlimitsTag, EOCustUnlimitsTag, Long>{

	UICustUnlimitsTag getCurrent(Map<String, List<String>> headers);

}
