package com.brijframework.client.device.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brijframework.util.casting.DateUtil;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.client.constants.Constants;
import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.device.mapper.DeviceUnlimitsVisualizeMapper;
import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.device.model.UIDeviceVisualizeRequest;
import com.brijframework.client.entities.EOUnlimits;
import com.brijframework.client.entities.EOUnlimitsItem;
import com.brijframework.client.entities.EOUnlimitsVisualize;
import com.brijframework.client.exceptions.InvalidParameterException;
import com.brijframework.client.forgin.model.ClientBoardingAnswerModel;
import com.brijframework.client.forgin.model.ClientOnBoardingQuestionModel;
import com.brijframework.client.forgin.model.PromptModel;
import com.brijframework.client.forgin.repository.ChatGptClient;
import com.brijframework.client.forgin.repository.OnboardingClient;
import com.brijframework.client.forgin.repository.PromptClient;
import com.brijframework.client.repository.UnlimitsVisualizeRepository;

@Service("DeviceVisualizeService")
public class DeviceVisualizeServiceImpl extends DeviceUnlimitVisualizeServiceImpl
		implements DeviceVisualizeService {

	private static final String SUB_CATEGORY_ID = "subCategoryId";

	@Autowired
	private PromptClient promptClient;

	@Autowired
	private OnboardingClient onboardingClient;

	@Autowired
	private ChatGptClient chatGptClient;

	@Autowired
	private DeviceUnlimitsService clientUnlimitsService;

	@Autowired
	private UnlimitsVisualizeRepository unlimitsVisualizeRepository;

	@Autowired
	private DeviceUnlimitsVisualizeMapper deviceUnlimitsVisualizeMapper;

	@Override
	public JpaRepository<EOUnlimitsVisualize, Long> getRepository() {
		return unlimitsVisualizeRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsVisualize, UIDeviceUnlimitsVisualize> getMapper() {
		return deviceUnlimitsVisualizeMapper;
	}

	@Override
	public List<UIDeviceUnlimits> findAllUnlimits(Map<String, List<String>> headers,
			Map<String, Object> filters, Map<String, Object> actions) {
		List<UIDeviceUnlimits> custUnlimitsTags = clientUnlimitsService.findAll(headers, filters, actions);

		List<UIDeviceUnlimits> uiDeviceUnlimits= new ArrayList<UIDeviceUnlimits>() {
			private static final long serialVersionUID = 1L;
			{
				addAll(custUnlimitsTags.stream().filter(custUnlimitsTag->!CollectionUtils.isEmpty(custUnlimitsTag.getItems())).toList());
			}
		};
		uiDeviceUnlimits.sort((uiDeviceUnlimits1,uiDeviceUnlimits2)->uiDeviceUnlimits2.getUnlimitsDate().compareTo(uiDeviceUnlimits1.getUnlimitsDate()));
		return uiDeviceUnlimits;
	}
	
	@Override
	public UIDeviceUnlimits findUnlimits(UIDeviceVisualizeRequest clientVisualizeRequest, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if(clientVisualizeRequest.getUnlmitsType()==null) {
			throw new IllegalArgumentException("Unexpected value: " + clientVisualizeRequest.getType());
		}
		Map<String, Object> action=new HashMap<String, Object>();
		if(clientVisualizeRequest.getSubCategoryId()!=null)
		filters.put(SUB_CATEGORY_ID, clientVisualizeRequest.getSubCategoryId());
		switch (clientVisualizeRequest.getUnlmitsType()) {
		case WORDS: {
			return clientUnlimitsService.findById(clientVisualizeRequest.getUnlimitsId(),  headers, filters, action);
		}
		case IMAGE: {
			return clientUnlimitsService.findById(clientVisualizeRequest.getUnlimitsId(),  headers, filters, action);
		}
		case EXAMPLE: {
			return clientUnlimitsService.findById(clientVisualizeRequest.getUnlimitsId(),  headers, filters, action);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + clientVisualizeRequest.getType());
		}
	}

	@Override
	public UIDeviceUnlimitsVisualize requestUnlimits(UIDeviceVisualizeRequest clientVisualizeRequest, Map<String, List<String>> headers,Map<String, Object> filters, Map<String, Object> actions) {
		if(clientVisualizeRequest.getUnlmitsType()==null) {
			throw new IllegalArgumentException("Unexpected value: " + clientVisualizeRequest.getType());
		}
		switch (clientVisualizeRequest.getUnlmitsType()) {
		case WORDS: {
			return buildVisualizeByWords(clientVisualizeRequest,  headers);
		}
		case IMAGE: {
			return buildVisualizeByImage(clientVisualizeRequest, headers);
		}
		case EXAMPLE: {
			return buildVisualizeByExample(clientVisualizeRequest, headers);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + clientVisualizeRequest.getType());
		}
	}

	private UIDeviceUnlimitsVisualize buildVisualizeByWords(UIDeviceVisualizeRequest request,  Map<String, List<String>> headers) {
		EOUnlimits unlimitsTag = clientUnlimitsRepository.findById(request.getUnlimitsId()).orElseThrow(() -> new RuntimeException("Invalid unlimt!"));
		List<EOUnlimitsItem> tagItems = unlimitsTag.getItems().stream().filter(item -> item.getYear().equals(request.getYear()) && item.getSubCategoryId().equals(request.getSubCategoryId()) ).toList();
		return buildVisualizeByWordsBySubCategory( request,unlimitsTag, tagItems, headers);
	}

	private UIDeviceUnlimitsVisualize buildVisualizeByWordsBySubCategory(UIDeviceVisualizeRequest deviceVisualizeRequest, EOUnlimits unlimitsTag,
			List<EOUnlimitsItem> tagItemList,  Map<String, List<String>> headers) {
		UIDeviceUnlimitsVisualize buildVisualize = buildVisualize(deviceVisualizeRequest.getYear());
		StringBuffer request = new StringBuffer(buildVisualize.getVisualizeRequest());
		PromptModel subCategoryPrompt = getPrompt(promptClient.getPromptsBySubCategory(deviceVisualizeRequest.getSubCategoryId()));
		if (subCategoryPrompt != null && StringUtil.isNonEmpty(subCategoryPrompt.getDescription())) {
			request.append(subCategoryPrompt.getDescription());
		}
		List<EOUnlimitsItem> tagItemListByYear = tagItemList.stream().filter(tagItem->deviceVisualizeRequest.getYear().equals(tagItem.getYear()) && deviceVisualizeRequest.getSubCategoryId().equals(tagItem.getSubCategoryId())).toList();
		if(!CollectionUtils.isEmpty(tagItemListByYear)) {
			for (EOUnlimitsItem clientUnlimitsTagItem : tagItemListByYear) {
				String tagName = clientUnlimitsTagItem.getTagName();
				addTagNameFromTag(request, tagName);
			}
			String visualizeRequest = request.toString();
			buildVisualize.setVisualizeRequest(visualizeRequest);
			buildVisualize.setVisualizeResponse(buildCptResponse(visualizeRequest, buildVisualize));
			buildVisualize.setVisualizeYear(deviceVisualizeRequest.getYear());
			buildVisualize.setSubCategoryId(deviceVisualizeRequest.getSubCategoryId());
			buildVisualize.setEoUnlimits(unlimitsTag);
			buildVisualize.setUnlimitsId(deviceVisualizeRequest.getUnlimitsId());
			buildVisualize.setType(UnlimitsType.WORDS.getType());
			buildVisualize.setSubCategoryId(deviceVisualizeRequest.getSubCategoryId());
			return add(buildVisualize, headers);
		}
		return buildVisualize;
	}

	private String buildCptResponse(String prompts, UIDeviceUnlimitsVisualize buildVisualize) {
		try {
			return chatGptClient.getTextResult(prompts);
		}catch (RuntimeException e) {
			e.printStackTrace();
			buildVisualize.setErrorCode(504);
			buildVisualize.setErrorMsg("Due to some technical issue. Please try after sometime.");
			buildVisualize.setTraceMsg(e.getMessage());
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			buildVisualize.setErrorCode(504);
			buildVisualize.setErrorMsg("Due to some technical issue. Please try after sometime.");
			buildVisualize.setTraceMsg(e.getMessage());
			return null;
		}
	}

	private boolean addTagNameFromTag(StringBuffer request, String tagName) {
		if (StringUtil.isEmpty(tagName)) {
			return false;
		}
		if (request.length() > 0) {
			request.append(" ");
		}
		request.append(tagName);
		return true;
	}

	private UIDeviceUnlimitsVisualize buildVisualizeByImage(UIDeviceVisualizeRequest visualizeRequest, Map<String, List<String>> headers) {
		EOUnlimits unlimitsImage = clientUnlimitsRepository.findById(visualizeRequest.getUnlimitsId())
				.orElseThrow(() -> new RuntimeException("Invalid unlimt!"));

		List<EOUnlimitsItem> imageItems = unlimitsImage.getItems().stream()
				.filter(item -> item.getYear().equals(visualizeRequest.getYear()) && item.getSubCategoryId().equals(visualizeRequest.getSubCategoryId()) ).toList();
		return buildVisualizeByImageBySubCategory(visualizeRequest, unlimitsImage, imageItems, headers);
	}

	private UIDeviceUnlimitsVisualize buildVisualizeByImageBySubCategory(UIDeviceVisualizeRequest deviceVisualizeRequest, EOUnlimits unlimitsImage, List<EOUnlimitsItem> imageItems, Map<String, List<String>> headers) {
		UIDeviceUnlimitsVisualize buildVisualize = buildVisualize(deviceVisualizeRequest.getYear());
		StringBuffer request = new StringBuffer(buildVisualize.getVisualizeRequest());
		
		PromptModel subCategoryPrompt = getPrompt(promptClient.getPromptsBySubCategory(deviceVisualizeRequest.getSubCategoryId()));
		if (subCategoryPrompt != null && StringUtil.isNonEmpty(subCategoryPrompt.getDescription())) {
			request.append(subCategoryPrompt.getDescription());
		}
		for (EOUnlimitsItem clientUnlimitsImageItem : imageItems) {
			String tagName = clientUnlimitsImageItem.getTagName();
			if (addTagNameFromTag(request, tagName)) {
				continue;
			}
			String imageUrl = clientUnlimitsImageItem.getImageUrl();
			addTagNameFromImage(request, imageUrl);
		}
		String finalrequest = request.toString();
		buildVisualize.setVisualizeRequest(finalrequest);
		buildVisualize.setVisualizeResponse(buildCptResponse(finalrequest, buildVisualize));
		buildVisualize.setVisualizeYear(deviceVisualizeRequest.getYear());
		buildVisualize.setEoUnlimits(unlimitsImage);
		buildVisualize.setUnlimitsId(deviceVisualizeRequest.getUnlimitsId());
		buildVisualize.setType(UnlimitsType.IMAGE.getType());
		buildVisualize.setSubCategoryId(deviceVisualizeRequest.getSubCategoryId());
		return add(buildVisualize, headers);
	}

	private UIDeviceUnlimitsVisualize buildVisualizeByExample(UIDeviceVisualizeRequest visualizeRequest, Map<String, List<String>> headers) {
		EOUnlimits unlimitsExample = clientUnlimitsRepository.findById(visualizeRequest.getUnlimitsId()).orElseThrow(() -> new InvalidParameterException("Invalid unlimitId!"));
		
		List<EOUnlimitsItem> exampleItems = unlimitsExample.getItems().stream().filter(item->item.getYear().equals(visualizeRequest.getYear()) && item.getSubCategoryId().equals(visualizeRequest.getSubCategoryId())).toList();
		return buildVisualizeByExampleBySubCategory(visualizeRequest, headers, unlimitsExample, exampleItems);
	}

	private UIDeviceUnlimitsVisualize buildVisualizeByExampleBySubCategory(UIDeviceVisualizeRequest visualizeRequest,
			Map<String, List<String>> headers, EOUnlimits unlimitsExample,
			List<EOUnlimitsItem> exampleItems) {
		UIDeviceUnlimitsVisualize buildVisualize = buildVisualize(visualizeRequest.getYear());
		StringBuffer request = new StringBuffer(buildVisualize.getVisualizeRequest());
		for (EOUnlimitsItem clientUnlimitsExampleItem : exampleItems) {
			String tagName = clientUnlimitsExampleItem.getTagName();
			if (addTagNameFromTag(request, tagName)) {
				continue;
			}
			String imageUrl = clientUnlimitsExampleItem.getImageUrl();
			addTagNameFromImage(request, imageUrl);
		}
		String finalRequest = request.toString();
		buildVisualize.setVisualizeRequest(finalRequest);
		buildVisualize.setVisualizeResponse(buildCptResponse(finalRequest, buildVisualize));
		buildVisualize.setVisualizeYear(visualizeRequest.getYear());
		buildVisualize.setEoUnlimits(unlimitsExample);
		buildVisualize.setUnlimitsId(visualizeRequest.getUnlimitsId());
		buildVisualize.setType(UnlimitsType.EXAMPLE.getType());
		return add(buildVisualize, headers);
	}

	private void addTagNameFromImage(StringBuffer request, String imageUrl) {
		if (StringUtil.isEmpty(imageUrl)) {
			return;
		}
		String[] tagList = imageUrl.split("\\.")[0].split(",");
		for (String tagName : tagList) {
			if (request.length() > 0) {
				request.append(" ");
			}
			request.append(tagName);
		}
	}

	private UIDeviceUnlimitsVisualize buildVisualize(Integer year) {
		PromptModel yearPrompt = getPrompt(promptClient.getPromptsByYear(year));
		StringBuffer request = new StringBuffer();
		if (yearPrompt != null && StringUtil.isNonEmpty(yearPrompt.getDescription())) {
			request.append(yearPrompt.getDescription());
		}
		List<ClientOnBoardingQuestionModel> onboardings = getBoardingQuestion(onboardingClient.getOnboardings());
		if (!CollectionUtils.isEmpty(onboardings)) {
			onboardings.sort((q1, q2) -> q1.getQuestion().getOrderSequence().compareTo(q1.getQuestion().getOrderSequence()));
			for (ClientOnBoardingQuestionModel clientOnBoardingQuestion : onboardings) {
				if (CollectionUtils.isEmpty(clientOnBoardingQuestion.getAnswers())) {
					continue;
				}
				request.append(clientOnBoardingQuestion.getQuestion().getQuestion() + " is ");
				for (ClientBoardingAnswerModel answer : clientOnBoardingQuestion.getAnswers()) {
					request.append(answer.getValue() + ".");
				}
			}
		}
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.YEAR, year);
		UIDeviceUnlimitsVisualize uiVisualize = new UIDeviceUnlimitsVisualize();
		uiVisualize.setVisualizeDate(DateUtil.getDateStringForPattern(instance.getTime(), Constants.UI_DATE_FORMAT_MM_DD_YY));
		uiVisualize.setVisualizeRequest(request.toString());
		return uiVisualize;
	}

	private PromptModel getPrompt(Response<List<PromptModel>> response) {
		if ("1".equals(response.getSuccess())) {
			List<PromptModel> data = response.getData();
			if (CollectionUtils.isEmpty(data)) {
				return null;
			}
			return data.get(0);
		}
		return null;
	}

	private List<ClientOnBoardingQuestionModel> getBoardingQuestion(Response<List<ClientOnBoardingQuestionModel>> response) {
		if ("1".equals(response.getSuccess())) {
			return response.getData();
		}
		return null;
	}

}
