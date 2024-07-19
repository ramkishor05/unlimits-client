/**
 * 
 */
package com.brijframework.client.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.unlimits.entities.EOClientUnlimitsExample;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientUnlimitsExampleRepository extends CustomRepository<EOClientUnlimitsExample, Long>{

	
	@Query(nativeQuery = true,  value = "SELECT COALESCE(MAX(id), 0)+1 FROM EOCLIENT_UNLIMITS_EXAMPLE where CUST_BUSINESS_APP_ID=?1")
	int getMaxTransactionId(Long custBusniessAppId);
	
}
