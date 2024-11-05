package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCoachSession;

@Repository
public interface CoachConversionSessionRepository extends CustomRepository<EOCoachSession, Long>{

	String EOUNLIMIT_COACH_CONVERSION = "EOUNLIMIT_COACH_CONVERSION";

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOUNLIMIT_COACH_CONVERSION+" where CUST_BUSINESS_APP_ID=?1 and COACH_DATE = current_date() and RECORD_STATUS in (?2) ")
	List<EOCoachSession> findTodayJournalLibarary(Long eoCustBusinessAppId,List<String> statusIds);

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOUNLIMIT_COACH_CONVERSION+" where CUST_BUSINESS_APP_ID=?1 and COACH_DATE = DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY) and RECORD_STATUS in (?2)")
	List<EOCoachSession> findYesterdaySession(Long eoCustBusinessAppId,List<String> statusIds);

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOUNLIMIT_COACH_CONVERSION+" where CUST_BUSINESS_APP_ID=?1 and COACH_DATE = (select max(COACH_DATE) FROM "+EOUNLIMIT_COACH_CONVERSION+" and RECORD_STATUS in (?2))")
	List<EOCoachSession> findLastSession(Long eoCustBusinessAppId, List<String> statusIds);

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOUNLIMIT_COACH_CONVERSION+" where CUST_BUSINESS_APP_ID=?1 and COACH_DATE between ?2 and ?3  and RECORD_STATUS in (?4)")
	List<EOCoachSession> findAllByCoachDateDateRange(Long eoCustBusinessAppId, java.sql.Date toStart,
			java.sql.Date endDate, List<String> statusIds);


}
