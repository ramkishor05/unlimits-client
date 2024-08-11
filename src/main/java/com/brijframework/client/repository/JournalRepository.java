/**
 * 
 */
package com.brijframework.client.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOJournal;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface JournalRepository extends CustomRepository<EOJournal, Long>{

	List<EOJournal> findAllByCustBusinessAppAndJournalId(EOCustBusinessApp eoCustBusinessApp, Long journalId);
	
}
