/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

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

	List<EOUnlimitsVisualize> findAllByUnlimitsExampleId(Long id);
	
	List<EOUnlimitsVisualize> findAllByUnlimitsImageId(Long id);
	
	List<EOUnlimitsVisualize> findAllByUnlimitsTagId(Long id);

	java.util.Optional<EOUnlimitsVisualize> findOneByUnlimitsExampleIdAndVisualizeYear(Long id, Integer visualizeYear);
	
	java.util.Optional<EOUnlimitsVisualize> findOneByUnlimitsImageIdAndVisualizeYear(Long id, Integer visualizeYear);

	java.util.Optional<EOUnlimitsVisualize> findOneByUnlimitsTagIdAndVisualizeYear(Long id, Integer visualizeYear);

}
