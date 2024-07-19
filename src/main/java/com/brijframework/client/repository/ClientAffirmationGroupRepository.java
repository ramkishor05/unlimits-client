package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.unlimits.entities.EOClientAffirmationGroup;

@Repository
public interface ClientAffirmationGroupRepository extends CustomRepository<EOClientAffirmationGroup, Long>{

	List<EOClientAffirmationGroup> findAllByCustBusinessApp(Specification<EOClientAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp, Sort descending);

	Page<EOClientAffirmationGroup> findAllByCustBusinessApp(Specification<EOClientAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

}
