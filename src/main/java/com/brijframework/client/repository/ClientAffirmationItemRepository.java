package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.unlimits.entities.EOClientAffirmationItem;

@Repository
public interface ClientAffirmationItemRepository extends CustomRepository<EOClientAffirmationItem, Long>{

}
