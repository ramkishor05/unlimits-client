package com.brijframework.client.device.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.brijframework.util.casting.DateUtil;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.client.constants.Constants;
import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.device.model.UIDeviceUnlimitsExample;
import com.brijframework.client.device.model.UIDeviceUnlimitsImage;
import com.brijframework.client.device.model.UIDeviceUnlimitsTag;
import com.brijframework.client.device.model.UIDeviceVisualizeRequest;
import com.brijframework.client.device.model.UIDeviceVisualizeResponse;
import com.brijframework.client.entities.EOUnlimitsExample;
import com.brijframework.client.entities.EOUnlimitsExampleItem;
import com.brijframework.client.entities.EOUnlimitsImage;
import com.brijframework.client.entities.EOUnlimitsImageItem;
import com.brijframework.client.entities.EOUnlimitsTag;
import com.brijframework.client.entities.EOUnlimitsTagItem;
import com.brijframework.client.forgin.model.ClientBoardingAnswer;
import com.brijframework.client.forgin.model.ClientOnBoardingQuestion;
import com.brijframework.client.forgin.model.PromptLibarary;
import com.brijframework.client.forgin.repository.ChatGptClient;
import com.brijframework.client.forgin.repository.OnboardingClient;
import com.brijframework.client.forgin.repository.PromptClient;
import com.brijframework.client.repository.UnlimitsExampleRepository;
import com.brijframework.client.repository.UnlimitsImageRepository;
import com.brijframework.client.repository.UnlimitsTagRepository;

@Service
public class DeviceVisualizeServiceImpl implements DeviceVisualizeService {

	@Autowired
	private UnlimitsExampleRepository clientUnlimitsExampleRepository;
	
	@Autowired
	private UnlimitsImageRepository clientUnlimitsImageRepository;
	
	@Autowired
	private UnlimitsTagRepository clientUnlimitsTagRepository;
	
	@Autowired
	private PromptClient promptClient;
	
	@Autowired
	private OnboardingClient onboardingClient;
	
	@Autowired
	private ChatGptClient chatGptClient;
	
	@Autowired
	private DeviceUnlimitsTagService clientUnlimitsTagService;
	
	@Autowired
	private DeviceUnlimitsImageService clientUnlimitsImageService;
	
	@Autowired
	private DeviceUnlimitsExampleService clientUnlimitsExampleService;
	
	@Override
	public List<UIDeviceUnlimits> findAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		List<UIDeviceUnlimitsTag> custUnlimitsTags = clientUnlimitsTagService.findAll(headers, filters);
		
		List<UIDeviceUnlimitsImage> custUnlimitsImages = clientUnlimitsImageService.findAll(headers, filters);
		
		List<UIDeviceUnlimitsExample> custUnlimitsExamples = clientUnlimitsExampleService.findAll(headers, filters);
		
		return new ArrayList<UIDeviceUnlimits>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			addAll(custUnlimitsTags);
			addAll(custUnlimitsImages);
			addAll(custUnlimitsExamples);
		}
		};
	}


	@Override
	public UIDeviceVisualizeResponse add(UIDeviceVisualizeRequest clientVisualizeRequest) {
		switch (clientVisualizeRequest.getType()) {
		case WORDS: {
			return buildVisualizeByWords(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId());
		}
		case IMAGE: {
			return buildVisualizeByImage(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId());
		}
		case EXAMPLE: {
			return buildVisualizeByExample(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId());
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + clientVisualizeRequest.getType());
		}
	}

	private UIDeviceVisualizeResponse buildVisualizeByWords(Integer year, Long unlimitId) {
		EOUnlimitsTag unlimitsTag = clientUnlimitsTagRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsTag.getSubCategoryId();
		UIDeviceVisualizeResponse buildVisualize = buildVisualize(year, subCategoryId);
		List<EOUnlimitsTagItem> tagItems = unlimitsTag.getTagItems();
		StringBuffer request=new StringBuffer(buildVisualize.getVisualizeRequest());
		for(EOUnlimitsTagItem clientUnlimitsTagItem: tagItems) {
			String tagName=clientUnlimitsTagItem.getTagName();
			addTagNameFromTag(request, tagName);
		}
		String visualizeRequest = request.toString();
		buildVisualize.setVisualizeRequest(visualizeRequest);
		buildVisualize.setVisualizeResponse(getResponse(visualizeRequest));
		return buildVisualize;
	}


	private String getResponse(String prompts) {
		return chatGptClient.getTextResult(prompts);
	}


	private boolean addTagNameFromTag(StringBuffer request, String tagName) {
		if(StringUtil.isEmpty(tagName)) {
			return false;
		}
		if(request.length()>0) {
			request.append(" ");
		}
		request.append(tagName);
		return true;
	}
	
	private UIDeviceVisualizeResponse buildVisualizeByImage(Integer year, Long unlimitId) {
		EOUnlimitsImage unlimitsImage = clientUnlimitsImageRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsImage.getSubCategoryId();
		UIDeviceVisualizeResponse buildVisualize = buildVisualize(year, subCategoryId);
		List<EOUnlimitsImageItem> imageItems = unlimitsImage.getImageItems();
		StringBuffer request=new StringBuffer(buildVisualize.getVisualizeRequest());
		for(EOUnlimitsImageItem clientUnlimitsImageItem: imageItems) {
			String imageUrl=clientUnlimitsImageItem.getImageUrl();
			addTagNameFromImage(request, imageUrl);
		}
		String visualizeRequest = request.toString();
		buildVisualize.setVisualizeRequest(visualizeRequest);
		buildVisualize.setVisualizeResponse(getResponse(visualizeRequest));
		return buildVisualize;
	}
	
	private UIDeviceVisualizeResponse buildVisualizeByExample(Integer year, Long unlimitId) {
		EOUnlimitsExample unlimitsExample = clientUnlimitsExampleRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsExample.getSubCategoryId();
		UIDeviceVisualizeResponse buildVisualize = buildVisualize(year, subCategoryId);
		List<EOUnlimitsExampleItem> exampleItems = unlimitsExample.getExampleItems();
		StringBuffer request=new StringBuffer(buildVisualize.getVisualizeRequest());
		for(EOUnlimitsExampleItem clientUnlimitsExampleItem: exampleItems) {
			
			String tagName = clientUnlimitsExampleItem.getTagName();
			
			if(addTagNameFromTag(request, tagName)) {
				continue;
			}
			
			String imageUrl=clientUnlimitsExampleItem.getImageUrl();
			addTagNameFromImage(request, imageUrl);
		}
		String visualizeRequest = request.toString();
		buildVisualize.setVisualizeRequest(visualizeRequest);
		buildVisualize.setVisualizeResponse(getResponse(visualizeRequest));
		return buildVisualize;
	}


	private void addTagNameFromImage(StringBuffer request, String imageUrl) {
		if(StringUtil.isEmpty(imageUrl)) {
			return;
		}
		String[] tagList = imageUrl.split("\\.")[0].split(",");
		for(String tagName: tagList) {
			if(request.length()>0) {
				request.append(" ");
			}
			request.append(tagName);
		}
	}

	private UIDeviceVisualizeResponse buildVisualize(Integer year, Long subCategoryId) {
		PromptLibarary yearPrompt = getPrompt(promptClient.getPromptsByYear(year));
		PromptLibarary subCategoryPrompt = getPrompt(promptClient.getPromptsBySubCategory(subCategoryId));
		StringBuffer request=new StringBuffer();
		if(yearPrompt!=null && StringUtil.isNonEmpty(yearPrompt.getDescription())) {
			request.append(yearPrompt.getDescription());
		}
		if(subCategoryPrompt!=null && StringUtil.isNonEmpty(subCategoryPrompt.getDescription())) {
			request.append(subCategoryPrompt.getDescription());
		}
		
		List<ClientOnBoardingQuestion> onboardings = getBoardingQuestion(onboardingClient.getOnboardings());
		if(!CollectionUtils.isEmpty(onboardings)) {
			onboardings.sort((q1,q2)->q1.getQuestion().getOrderSequence().compareTo(q1.getQuestion().getOrderSequence()));
			
			for(ClientOnBoardingQuestion clientOnBoardingQuestion: onboardings) {
				if(CollectionUtils.isEmpty(clientOnBoardingQuestion.getAnswers())) {
					continue;
				}
				request.append(clientOnBoardingQuestion.getQuestion().getQuestion()+" is ");
				for(ClientBoardingAnswer answer:  clientOnBoardingQuestion.getAnswers()) {
					request.append(answer.getValue() +".");
				}
			}
		}
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.YEAR, year);
		UIDeviceVisualizeResponse uiVisualize=new UIDeviceVisualizeResponse();
		uiVisualize.setVisualizeDate(DateUtil.getDateStringForPattern(instance.getTime(), Constants.DEVICE_DATE_FORMAT_MMMM_DD_YYYY));
		uiVisualize.setVisualizeRequest(request.toString());
		return uiVisualize;
	}


	private PromptLibarary getPrompt(Response<List<PromptLibarary>> response) {
		if("1".equals(response.getSuccess())) {
			List<PromptLibarary> data = response.getData();
			if(CollectionUtils.isEmpty(data)) {
				return null;
			}
			return data.get(0);
		}
		return null;
	}
	
	private List<ClientOnBoardingQuestion> getBoardingQuestion(Response<List<ClientOnBoardingQuestion>> response) {
		if("1".equals(response.getSuccess())) {
			return response.getData();
		}
		return null;
	}

}
