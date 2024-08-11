package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOAffirmationGroup;

@Repository
public interface AffirmationGroupRepository extends CustomRepository<EOAffirmationGroup, Long>{

}
