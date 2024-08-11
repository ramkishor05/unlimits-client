package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.entities.EOMindSetItem;

@Repository
public interface MindSetItemRepository extends CustomRepository<EOMindSetItem, Long>{

}
