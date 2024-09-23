/**
 * 
 */
package com.brijframework.client.device.controller;
import static com.brijframework.client.constants.Constants.UI_DATE_FORMAT_MM_DD_YY;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.PageDetail;
import org.unlimits.rest.crud.beans.QueryRequest;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceJournalGroup;
import com.brijframework.client.device.model.UIDeviceJournalItem;
import com.brijframework.client.device.service.DeviceJournalService;
import com.brijframework.client.entities.EOJournal;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/journals"})
@CrossOrigin("*")
public class DeviceJournalController implements CrudController<UIDeviceJournalItem, EOJournal, Long>{
	
	@Autowired
	private DeviceJournalService clientJournalService;

	@Override
	public CrudService<UIDeviceJournalItem, EOJournal, Long> getService() {
		return clientJournalService;
	}
	
	@Override
	public Object customizedResponse(List<UIDeviceJournalItem> values, QueryRequest queryRequest ) {
		SimpleDateFormat dateFormat=new SimpleDateFormat(UI_DATE_FORMAT_MM_DD_YY);
		return values.stream().collect(Collectors.groupingBy(UIDeviceJournalItem::toJournalDate)).entrySet().stream().map(entry->new UIDeviceJournalGroup(dateFormat.format(entry.getKey()), entry.getValue())).toList();
	}
	
	@Override
	public Object customizedResponse(PageDetail<UIDeviceJournalItem> fetchPageObject, QueryRequest queryRequest) {
		SimpleDateFormat dateFormat=new SimpleDateFormat(UI_DATE_FORMAT_MM_DD_YY);
		List<UIDeviceJournalGroup> list = fetchPageObject.getElements().stream().collect(Collectors.groupingBy(UIDeviceJournalItem::toJournalDate)).entrySet().stream().map(entry->new UIDeviceJournalGroup(dateFormat.format(entry.getKey()), entry.getValue())).toList();
		PageDetail<UIDeviceJournalGroup> returnPageObject=new PageDetail<UIDeviceJournalGroup>();
		returnPageObject.setTotalCount(fetchPageObject.getTotalCount());
		returnPageObject.setPageCount(fetchPageObject.getPageCount());
		returnPageObject.setTotalPages(fetchPageObject.getTotalPages());
		returnPageObject.setElements(list);
		return returnPageObject;
	}

}
