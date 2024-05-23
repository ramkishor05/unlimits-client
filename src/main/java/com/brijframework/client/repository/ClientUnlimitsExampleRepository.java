/**
 * 
 */
package com.brijframework.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.client.unlimits.entities.EOClientUnlimitsExample;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientUnlimitsExampleRepository extends JpaRepository<EOClientUnlimitsExample, Long>{

}
