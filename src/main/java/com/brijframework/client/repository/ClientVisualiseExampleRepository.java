/**
 * 
 */
package com.brijframework.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.client.visualise.entities.EOClientVisualiseExample;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface ClientVisualiseExampleRepository extends JpaRepository<EOClientVisualiseExample, Long>{

}
