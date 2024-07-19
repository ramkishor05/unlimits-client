/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.unlimits.entities.EOClientAffirmationGroup;
import com.brijframework.client.unlimits.entities.EOClientCommitmentGroup;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientCommitmentGroupRepository extends CustomRepository<EOClientCommitmentGroup, Long>{

	/**
	 * @param eoCustBusinessApp
	 * @return
	 */
	List<EOClientCommitmentGroup> findAllByCustBusinessApp(Specification<EOClientAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp);

	/**
	 * @param eoCustBusinessApp
	 * @param pageable
	 * @return
	 */
	Page<EOClientCommitmentGroup> findAllByCustBusinessApp(Specification<EOClientAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

	/**
	 * @param eoCustBusinessApp
	 * @param sort
	 * @return
	 */
	List<EOClientCommitmentGroup> findAllByCustBusinessApp(Specification<EOClientAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp, Sort sort);

}
