package com.brijframework.client.global.mapper;

import static com.brijframework.client.constants.Constants.APP_CLIENT_PACKAGE_IMPL;
import static com.brijframework.client.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOMindSetItem;
import com.brijframework.client.global.model.UIGlobalMindSetItem;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalMindSetItemMapper extends GenericMapper<EOMindSetItem, UIGlobalMindSetItem>{

}
