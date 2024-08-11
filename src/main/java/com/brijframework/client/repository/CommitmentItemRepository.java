/**
 * 
 */
package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCommitmentItem;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface CommitmentItemRepository extends CustomRepository<EOCommitmentItem, Long>{

}
