/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.unlimits.entities.EOCustJournal;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientJournalRepository extends CustomRepository<EOCustJournal, Long>{

	List<EOCustJournal> findAllByCustBusinessAppAndJournalId(EOCustBusinessApp eoCustBusinessApp, Long journalId);
	
}
