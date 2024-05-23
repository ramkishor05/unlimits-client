/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.visualise.entities.EOClientVisualiseExample;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientVisualiseExampleRepository extends JpaRepository<EOClientVisualiseExample, Long>{

	/**
	 * @param eoCustBusinessApp
	 * @return
	 */
	List<EOClientVisualiseExample> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp);

	/**
	 * @param eoCustBusinessApp
	 * @param pageable
	 * @return
	 */
	Page<EOClientVisualiseExample> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

	/**
	 * @param eoCustBusinessApp
	 * @param sort
	 * @return
	 */
	List<EOClientVisualiseExample> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Sort sort);

}
