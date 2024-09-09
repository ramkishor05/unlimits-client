/**
 * 
 */
package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOUnlimitsTagItem;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface UnlimitsTagItemRepository extends CustomRepository<EOUnlimitsTagItem, Long>{

	void deleteByUnlimitsTagId(Long id);

}
