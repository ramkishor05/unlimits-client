package com.brijframework.client.repository;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.client.unlimits.entities.EOClientMindSetItem;

@Repository
public interface ClientMindSetItemRepository extends CustomRepository<EOClientMindSetItem, Long>{

}
