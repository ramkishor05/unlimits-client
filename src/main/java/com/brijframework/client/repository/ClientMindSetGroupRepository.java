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
import com.brijframework.client.unlimits.entities.EOClientMindSetGroup;

@Repository
public interface ClientMindSetGroupRepository extends CustomRepository<EOClientMindSetGroup, Long>{

	List<EOClientMindSetGroup> findAllByCustBusinessApp(Specification<EOClientAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp, Sort descending);

	Page<EOClientMindSetGroup> findAllByCustBusinessApp(Specification<EOClientAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

}
