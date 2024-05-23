/**
 * 
 */
package com.brijframework.client.visualise.controller;

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

import com.brijframework.client.visualise.entities.EOClientVisualiseImage;
import com.brijframework.client.visualise.model.UIClientVisualiseImage;
import com.brijframework.client.visualise.service.ClientVisualiseImageService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/client/visualise/image")
@CrossOrigin("*")
public class ClientVisualiseImageController extends CrudController<UIClientVisualiseImage, EOClientVisualiseImage, Long>{
	
	@Autowired
	private ClientVisualiseImageService clientVisualiseImageService;

	@Override
	public CrudService<UIClientVisualiseImage, EOClientVisualiseImage, Long> getService() {
		return clientVisualiseImageService;
	}
	
    @GetMapping("/current")
	public Response getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response response=new Response();
		try {
			response.setData(clientVisualiseImageService.getCurrent(headers));
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
