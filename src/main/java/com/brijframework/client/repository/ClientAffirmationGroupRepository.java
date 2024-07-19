package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.unlimits.entities.EOClientAffirmationGroup;

@Repository
public interface ClientAffirmationGroupRepository extends CustomRepository<EOClientAffirmationGroup, Long>{

}
