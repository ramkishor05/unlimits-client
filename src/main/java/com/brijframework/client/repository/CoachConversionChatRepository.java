package com.brijframework.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCoachChat;

@Repository
public interface CoachConversionChatRepository extends CustomRepository<EOCoachChat, Long>{

	String EOCOACH_CONVERSION_CHAT = "EOCOACH_CONVERSION_CHAT";

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOCOACH_CONVERSION_CHAT+" where COACH_SESSION_ID=?1 and COACH_DATE = (select min(COACH_DATE) FROM "+EOCOACH_CONVERSION_CHAT+" where COACH_SESSION_ID=?1 and RECORD_STATUS in (?2))")
	Optional<EOCoachChat> findFirstChat(Long sessionId,List<String> statusIds);

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOCOACH_CONVERSION_CHAT+" where COACH_SESSION_ID=?1 and COACH_DATE = (select max(COACH_DATE) FROM "+EOCOACH_CONVERSION_CHAT+" where COACH_SESSION_ID=?1 and RECORD_STATUS in (?2))")
	Optional<EOCoachChat> findLastChat(Long sessionId, List<String> statusIds);

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOCOACH_CONVERSION_CHAT+" where COACH_SESSION_ID=?1 and COACH_DATE between ?2 and ?3  and RECORD_STATUS in (?4)")
	List<EOCoachChat> findAllByCoachDateDateRange(Long sessionId, java.sql.Date toStart,
			java.sql.Date endDate, List<String> statusIds);


}
