/**
 * 
 */
package com.brijframework.client.mapper;

import static com.brijframework.client.constants.ClientConstants.*;

import org.mapstruct.Mapper;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.entities.EOClientUnlimits;
import com.brijframework.client.model.ClientUnlimitsDTO;

/**
 *  @author omnie
 */

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface ClientUnlimitsMapper extends GenericMapper<EOClientUnlimits, ClientUnlimitsDTO>{

}
