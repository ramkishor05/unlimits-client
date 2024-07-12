/**
 * 
 */
package com.brijframework.client.unlimits.device.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.PageDetail;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.device.service.DeviceClientJournalService;
import com.brijframework.client.unlimits.entities.EOClientJournal;
import com.brijframework.client.unlimits.model.UIClientJournal;
import com.brijframework.client.unlimits.model.UIClientJournalGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/client/journals", "/api/client/journals"})
@CrossOrigin("*")
public class DeviceClientJournalController implements CrudController<UIClientJournal, EOClientJournal, Long>{
	
	@Autowired
	private DeviceClientJournalService clientJournalService;

	@Override
	public CrudService<UIClientJournal, EOClientJournal, Long> getService() {
		return clientJournalService;
	}
	
	@Override
	public Object customizedResponse(List<UIClientJournal> values) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		return values.stream().collect(Collectors.groupingBy(UIClientJournal::toJournalDate)).entrySet().stream().map(entry->new UIClientJournalGroup(dateFormat.format(entry.getKey()), entry.getValue())).toList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object customizedResponse(PageDetail fetchPageObject) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		fetchPageObject.setElements(((List<UIClientJournal>)fetchPageObject.getElements()).stream().collect(Collectors.groupingBy(UIClientJournal::toJournalDate)).entrySet().stream().map(entry->new UIClientJournalGroup(dateFormat.format(entry.getKey()), entry.getValue())).toList());
		return fetchPageObject;
	}

	@Override
	public Object customizedResponse(Boolean delete) {
		return delete;
	}
}
