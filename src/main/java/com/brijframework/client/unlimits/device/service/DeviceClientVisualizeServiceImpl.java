package com.brijframework.client.unlimits.device.service;

import java.util.Calendar;
import java.util.List;

import org.brijframework.util.casting.DateUtil;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.brijframework.client.constants.ClientConstants;
import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.forgin.model.PromptLibarary;
import com.brijframework.client.forgin.model.PromptLibararyResponse;
import com.brijframework.client.forgin.repository.PromptClient;
import com.brijframework.client.repository.ClientUnlimitsExampleRepository;
import com.brijframework.client.repository.ClientUnlimitsImageRepository;
import com.brijframework.client.repository.CustUnlimitsTagRepository;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsExample;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsImage;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsTag;
import com.brijframework.client.unlimits.model.UIClientVisualize;

@Service
public class DeviceClientVisualizeServiceImpl implements DeviceClientVisualizeService {

	@Autowired
	private ClientUnlimitsExampleRepository clientUnlimitsExampleRepository;
	
	@Autowired
	private ClientUnlimitsImageRepository clientUnlimitsImageRepository;
	
	@Autowired
	private CustUnlimitsTagRepository clientUnlimitsTagRepository;
	
	@Autowired
	private PromptClient promptClient;

	@Override
	public UIClientVisualize getVisualize(Integer year, UnlimitsType type, Long unlimitId) {
		switch (type) {
		case WORDS: {
			return buildClientVisualizeByWords(year, unlimitId);
		}
		case IMAGE: {
			return buildClientVisualizeByImage(year, unlimitId);
		}
		case EXAMPLE: {
			return buildClientVisualizeByExample(year, unlimitId);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
	

	private UIClientVisualize buildClientVisualizeByWords(Integer year, Long unlimitId) {
		EOCustUnlimitsTag unlimitsTag = clientUnlimitsTagRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsTag.getSubCategoryId();
		return buildClientVisualize(year, subCategoryId);
	}
	
	private UIClientVisualize buildClientVisualizeByImage(Integer year, Long unlimitId) {
		EOCustUnlimitsImage unlimitsImage = clientUnlimitsImageRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsImage.getSubCategoryId();
		return buildClientVisualize(year, subCategoryId);
	}
	
	private UIClientVisualize buildClientVisualizeByExample(Integer year, Long unlimitId) {
		EOCustUnlimitsExample unlimitsExample = clientUnlimitsExampleRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsExample.getSubCategoryId();
		return buildClientVisualize(year, subCategoryId);
	}

	private UIClientVisualize buildClientVisualize(Integer year, Long subCategoryId) {
		PromptLibarary yearPrompt = getPrompt(promptClient.getPromptsByYear(year));
		System.out.println("yearPrompt="+yearPrompt);
		PromptLibarary subCategoryPrompt = getPrompt(promptClient.getPromptsBySubCategory(subCategoryId));
		System.out.println("subCategoryPrompt="+yearPrompt);
		StringBuffer profile=new StringBuffer();
		if(yearPrompt!=null && StringUtil.isNonEmpty(yearPrompt.getDescription())) {
			profile.append(yearPrompt.getDescription());
		}
		if(subCategoryPrompt!=null && StringUtil.isNonEmpty(subCategoryPrompt.getDescription())) {
			if(profile.length()>0) {
				profile.append("\r\n");
			}
			profile.append(subCategoryPrompt.getDescription());
		}
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.YEAR, year);
		UIClientVisualize uiClientVisualize=new UIClientVisualize();
		uiClientVisualize.setVisualizeDate(DateUtil.getDateStringForPattern(instance.getTime(), ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY));
		uiClientVisualize.setVisualizeRequest(profile.toString());
		return uiClientVisualize;
	}


	private PromptLibarary getPrompt(PromptLibararyResponse response) {
		if("1".equals(response.getSuccess())) {
			List<PromptLibarary> data = response.getData();
			if(CollectionUtils.isEmpty(data)) {
				return null;
			}
			return data.get(0);
		}
		return null;
	}


}
