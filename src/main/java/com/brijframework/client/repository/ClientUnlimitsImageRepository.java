/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsImage;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientUnlimitsImageRepository extends CustomRepository<EOClientUnlimitsImage, Long>{

	/**
	 * @param eoCustBusinessApp
	 * @return
	 */
	List<EOClientUnlimitsImage> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp);

	/**
	 * @param eoCustBusinessApp
	 * @param pageable
	 * @return
	 */
	Page<EOClientUnlimitsImage> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

	/**
	 * @param eoCustBusinessApp
	 * @param sort
	 * @return
	 */
	List<EOClientUnlimitsImage> findAllByCustBusinessApp(EOCustBusinessApp eoCustBusinessApp, Sort sort);

	@Query(nativeQuery = true,  value = "SELECT COALESCE(MAX(id), 0)+1 FROM EOCLIENT_UNLIMITS_IMAGE where CUST_BUSINESS_APP_ID=?1")
	int getMaxTransactionId(Long custBusniessAppId);
}
