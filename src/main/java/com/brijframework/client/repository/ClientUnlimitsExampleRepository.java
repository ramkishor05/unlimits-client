/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsExample;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientUnlimitsExampleRepository extends JpaRepository<EOClientUnlimitsExample, Long>{

	/**
	 * @param eoCustBusinessApp
	 * @return
	 */
	List<EOClientUnlimitsExample> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp);

	/**
	 * @param eoCustBusinessApp
	 * @param pageable
	 * @return
	 */
	Page<EOClientUnlimitsExample> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

	/**
	 * @param eoCustBusinessApp
	 * @param sort
	 * @return
	 */
	List<EOClientUnlimitsExample> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Sort sort);


	@Query(nativeQuery = true,  value = "SELECT COALESCE(MAX(id), 0)+1 FROM EOCLIENT_UNLIMITS_EXAMPLE where CUST_BUSINESS_APP_ID=?1")
	int getMaxTransactionId(Long custBusniessAppId);
	
}
