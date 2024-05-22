/**
 * 
 */
package com.brijframework.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.visualise.EOClientVisualiseTag;
import com.brijframework.client.model.visualise.UIClientVisualiseTag;
import com.brijframework.client.service.ClientVisualiseTagService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/client/visualise/tag")
@CrossOrigin("*")
public class ClientVisualiseTagController extends CrudController<UIClientVisualiseTag, EOClientVisualiseTag, Long>{
	
	@Autowired
	private ClientVisualiseTagService clientVisualiseTagService;

	@Override
	public CrudService<UIClientVisualiseTag, EOClientVisualiseTag, Long> getService() {
		return clientVisualiseTagService;
	}
	
    @GetMapping("/current")
	public Response getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response response=new Response();
		try {
			response.setData(clientVisualiseTagService.getCurrent(headers));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
