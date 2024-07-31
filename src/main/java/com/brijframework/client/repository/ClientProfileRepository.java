package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.unlimits.entities.EOClientProfile;

@Repository
public interface ClientProfileRepository extends CustomRepository<EOClientProfile, Long>{

}
