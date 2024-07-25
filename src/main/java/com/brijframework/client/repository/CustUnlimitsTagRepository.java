/**
 * 
 */
package com.brijframework.client.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.unlimits.entities.EOCustUnlimitsTag;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface CustUnlimitsTagRepository extends CustomRepository<EOCustUnlimitsTag, Long>{

	@Query(nativeQuery = true,  value = "SELECT COALESCE(MAX(id), 0)+1 FROM EOCLIENT_UNLIMITS_TAG where CUST_BUSINESS_APP_ID=?1")
	int getMaxTransactionId(Long custBusniessAppId);
}
