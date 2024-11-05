/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOUnlimitsVisualize;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface UnlimitsVisualizeRepository extends CustomRepository<EOUnlimitsVisualize, Long>{

	List<EOUnlimitsVisualize> findAllByUnlimitsId(Long id);
	
	Optional<EOUnlimitsVisualize> findOneByUnlimitsIdAndVisualizeYear(Long id, Integer visualizeYear);
	
}
