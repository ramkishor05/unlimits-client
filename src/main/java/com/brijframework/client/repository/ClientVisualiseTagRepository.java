/**
 * 
 */
package com.brijframework.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.client.visualise.entities.EOClientVisualiseTag;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientVisualiseTagRepository extends JpaRepository<EOClientVisualiseTag, Long>{

}
