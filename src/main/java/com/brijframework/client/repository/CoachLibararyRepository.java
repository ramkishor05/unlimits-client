package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOUnlimitsCoachConversion;

@Repository
public interface CoachLibararyRepository extends CustomRepository<EOUnlimitsCoachConversion, Long>{


}
