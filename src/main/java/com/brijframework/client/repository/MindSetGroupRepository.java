package com.brijframework.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOAffirmationGroup;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOMindSetGroup;

@Repository
public interface MindSetGroupRepository extends CustomRepository<EOMindSetGroup, Long>{

	List<EOMindSetGroup> findAllByCustBusinessApp(Specification<EOAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp, Sort descending);

	Page<EOMindSetGroup> findAllByCustBusinessApp(Specification<EOAffirmationGroup> spec, EOCustBusinessApp eoCustBusinessApp, Pageable pageable);

}
