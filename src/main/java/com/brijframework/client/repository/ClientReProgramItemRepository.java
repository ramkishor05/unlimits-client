/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.unlimits.entities.EOClientReProgramGroup;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientReProgramItemRepository extends CustomRepository<EOClientReProgramGroup, Long>{

	/**
	 * @param eoCustBusinessApp
	 * @return
	 */
	List<EOClientReProgramGroup> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp);

	/**
	 * @param eoCustBusinessApp
	 * @param pageable
	 * @return
	 */
	Page<EOClientReProgramGroup> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

	/**
	 * @param eoCustBusinessApp
	 * @param sort
	 * @return
	 */
	List<EOClientReProgramGroup> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Sort sort);

}