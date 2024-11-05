/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOUnlimits;
import com.brijframework.client.repository.projections.UnlimtsProjecttion;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface UnlimitsRepository extends CustomRepository<EOUnlimits, Long>{

	
	@Query(nativeQuery = true,  value = "SELECT COALESCE(MAX(id), 0)+1 FROM EOCLIENT_UNLIMITS where CUST_BUSINESS_APP_ID=?1")
	int getMaxTransactionId(Long custBusniessAppId);
	
	@Query(nativeQuery = true,  value = ""
			+ " SELECT UTI.MAIN_CATEGORY_NAME AS mainCategoryName,"
			+ " UTI.MAIN_CATEGORY_ID AS mainCategoryId,"
			+ " UTI.SUB_CATEGORY_NAME AS subCategoryName,"
			+ " UTI.SUB_CATEGORY_ID AS subCategoryId,"
			+ " UTI.UNLIMITS_ID AS unlimitsId "
			+ " FROM EOCLIENT_UNLIMITS_ITEM UTI "
			+ " INNER JOIN EOCLIENT_UNLIMITS UT "
			+ " ON UTI.UNLIMITS_ID=UT.ID AND UT.RECORD_STATUS=UTI.RECORD_STATUS "
			+ " WHERE UT.RECORD_STATUS IN (?1) group by "
			+ " UTI.MAIN_CATEGORY_NAME, UTI.MAIN_CATEGORY_ID,"
			+ " UTI.SUB_CATEGORY_NAME, UTI.SUB_CATEGORY_ID,"
			+ " UTI.UNLIMITS_ID order by UTI.MAIN_CATEGORY_NAME, UTI.MAIN_CATEGORY_ID,"
			+ " UTI.SUB_CATEGORY_NAME, UTI.SUB_CATEGORY_ID,"
			+ " UTI.UNLIMITS_ID")
	List<UnlimtsProjecttion> findUnlimits(List<String> statusList);
	
}
