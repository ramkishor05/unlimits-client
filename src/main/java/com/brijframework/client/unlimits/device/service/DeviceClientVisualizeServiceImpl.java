package com.brijframework.client.unlimits.device.service;

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

import com.brijframework.client.constants.ClientConstants;
import com.brijframework.client.forgin.model.ClientBoardingAnswer;
import com.brijframework.client.forgin.model.ClientOnBoardingQuestion;
import com.brijframework.client.forgin.model.PromptLibarary;
import com.brijframework.client.forgin.repository.OnboardingClient;
import com.brijframework.client.forgin.repository.PromptClient;
import com.brijframework.client.repository.ClientUnlimitsExampleRepository;
import com.brijframework.client.repository.ClientUnlimitsImageRepository;
import com.brijframework.client.repository.CustUnlimitsTagRepository;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsExampleItem;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsImageItem;
import com.brijframework.client.unlimits.entities.EOClientUnlimitsTagItem;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsExample;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsImage;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsTag;
import com.brijframework.client.unlimits.model.UIClientUnlimits;
import com.brijframework.client.unlimits.model.UIClientVisualizeRequest;
import com.brijframework.client.unlimits.model.UIClientVisualizeResponse;
import com.brijframework.client.unlimits.model.UICustUnlimitsExample;
import com.brijframework.client.unlimits.model.UICustUnlimitsImage;
import com.brijframework.client.unlimits.model.UICustUnlimitsTag;

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
	
	@Autowired
	private OnboardingClient onboardingClient;
	
	@Autowired
	private DeviceClientUnlimitsTagService clientUnlimitsTagService;
	
	@Autowired
	private DeviceClientUnlimitsImageService clientUnlimitsImageService;
	
	@Autowired
	private DeviceClientUnlimitsExampleService clientUnlimitsExampleService;
	
	@Override
	public List<UIClientUnlimits> findAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		List<UICustUnlimitsTag> custUnlimitsTags = clientUnlimitsTagService.findAll(headers, filters);
		
		List<UICustUnlimitsImage> custUnlimitsImages = clientUnlimitsImageService.findAll(headers, filters);
		
		List<UICustUnlimitsExample> custUnlimitsExamples = clientUnlimitsExampleService.findAll(headers, filters);
		
		return new ArrayList<UIClientUnlimits>() {/**
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
	public UIClientVisualizeResponse add(UIClientVisualizeRequest clientVisualizeRequest) {
		switch (clientVisualizeRequest.getType()) {
		case WORDS: {
			return buildClientVisualizeByWords(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId());
		}
		case IMAGE: {
			return buildClientVisualizeByImage(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId());
		}
		case EXAMPLE: {
			return buildClientVisualizeByExample(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId());
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + clientVisualizeRequest.getType());
		}
	}

	private UIClientVisualizeResponse buildClientVisualizeByWords(Integer year, Long unlimitId) {
		EOCustUnlimitsTag unlimitsTag = clientUnlimitsTagRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsTag.getSubCategoryId();
		UIClientVisualizeResponse buildClientVisualize = buildClientVisualize(year, subCategoryId);
		List<EOClientUnlimitsTagItem> tagItems = unlimitsTag.getTagItems();
		StringBuffer request=new StringBuffer(buildClientVisualize.getVisualizeRequest());
		for(EOClientUnlimitsTagItem clientUnlimitsTagItem: tagItems) {
			String tagName=clientUnlimitsTagItem.getTagName();
			addTagNameFromTag(request, tagName);
		}
		String visualizeRequest = request.toString();
		buildClientVisualize.setVisualizeRequest(visualizeRequest);
		buildClientVisualize.setVisualizeResponse(getResponse(visualizeRequest));
		return buildClientVisualize;
	}


	private String getResponse(String visualizeRequest) {
		// TODO Auto-generated method stub
		return null;
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
	
	private UIClientVisualizeResponse buildClientVisualizeByImage(Integer year, Long unlimitId) {
		EOCustUnlimitsImage unlimitsImage = clientUnlimitsImageRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsImage.getSubCategoryId();
		UIClientVisualizeResponse buildClientVisualize = buildClientVisualize(year, subCategoryId);
		List<EOClientUnlimitsImageItem> imageItems = unlimitsImage.getImageItems();
		StringBuffer request=new StringBuffer(buildClientVisualize.getVisualizeRequest());
		for(EOClientUnlimitsImageItem clientUnlimitsImageItem: imageItems) {
			String imageUrl=clientUnlimitsImageItem.getImageUrl();
			addTagNameFromImage(request, imageUrl);
		}
		String visualizeRequest = request.toString();
		buildClientVisualize.setVisualizeRequest(visualizeRequest);
		buildClientVisualize.setVisualizeResponse(getResponse(visualizeRequest));
		return buildClientVisualize;
	}
	
	private UIClientVisualizeResponse buildClientVisualizeByExample(Integer year, Long unlimitId) {
		EOCustUnlimitsExample unlimitsExample = clientUnlimitsExampleRepository.findById(unlimitId).orElseThrow(()-> new RuntimeException("Invalid unlimt!"));
		Long subCategoryId = unlimitsExample.getSubCategoryId();
		UIClientVisualizeResponse buildClientVisualize = buildClientVisualize(year, subCategoryId);
		List<EOClientUnlimitsExampleItem> exampleItems = unlimitsExample.getExampleItems();
		StringBuffer request=new StringBuffer(buildClientVisualize.getVisualizeRequest());
		for(EOClientUnlimitsExampleItem clientUnlimitsExampleItem: exampleItems) {
			
			String tagName = clientUnlimitsExampleItem.getTagName();
			
			if(addTagNameFromTag(request, tagName)) {
				continue;
			}
			
			String imageUrl=clientUnlimitsExampleItem.getImageUrl();
			addTagNameFromImage(request, imageUrl);
		}
		String visualizeRequest = request.toString();
		buildClientVisualize.setVisualizeRequest(visualizeRequest);
		buildClientVisualize.setVisualizeResponse(getResponse(visualizeRequest));
		return buildClientVisualize;
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

	private UIClientVisualizeResponse buildClientVisualize(Integer year, Long subCategoryId) {
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
		UIClientVisualizeResponse uiClientVisualize=new UIClientVisualizeResponse();
		uiClientVisualize.setVisualizeDate(DateUtil.getDateStringForPattern(instance.getTime(), ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY));
		uiClientVisualize.setVisualizeRequest(request.toString());
		return uiClientVisualize;
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
