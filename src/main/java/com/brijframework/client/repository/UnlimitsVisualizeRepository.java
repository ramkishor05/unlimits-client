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

	List<EOUnlimitsVisualize> findAllByUnlimitsExampleId(Long id);
	
	List<EOUnlimitsVisualize> findAllByUnlimitsImageId(Long id);
	
	List<EOUnlimitsVisualize> findAllByUnlimitsTagId(Long id);

	Optional<EOUnlimitsVisualize> findOneByUnlimitsExampleIdAndVisualizeYear(Long id, Integer visualizeYear);
	
	Optional<EOUnlimitsVisualize> findOneByUnlimitsImageIdAndVisualizeYear(Long id, Integer visualizeYear);

	Optional<EOUnlimitsVisualize> findOneByUnlimitsTagIdAndVisualizeYear(Long id, Integer visualizeYear);

}
