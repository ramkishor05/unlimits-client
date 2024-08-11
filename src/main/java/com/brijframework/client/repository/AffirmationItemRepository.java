package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOAffirmationItem;

@Repository
public interface AffirmationItemRepository extends CustomRepository<EOAffirmationItem, Long>{

}
