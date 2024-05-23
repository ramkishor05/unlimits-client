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
import com.brijframework.client.visualise.entities.EOClientVisualiseImage;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientVisualiseImageRepository extends JpaRepository<EOClientVisualiseImage, Long>{

	/**
	 * @param eoCustBusinessApp
	 * @return
	 */
	List<EOClientVisualiseImage> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp);

	/**
	 * @param eoCustBusinessApp
	 * @param pageable
	 * @return
	 */
	Page<EOClientVisualiseImage> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

	/**
	 * @param eoCustBusinessApp
	 * @param sort
	 * @return
	 */
	List<EOClientVisualiseImage> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Sort sort);

}
