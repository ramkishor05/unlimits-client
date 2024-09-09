package com.brijframework.client.device.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brijframework.util.casting.DateUtil;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.constants.Constants;
import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceUnlimitsVisualizeMapper;
import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.device.model.UIDeviceUnlimitsExample;
import com.brijframework.client.device.model.UIDeviceUnlimitsImage;
import com.brijframework.client.device.model.UIDeviceUnlimitsTag;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.device.model.UIDeviceVisualizeRequest;
import com.brijframework.client.entities.EOUnlimitsExample;
import com.brijframework.client.entities.EOUnlimitsExampleItem;
import com.brijframework.client.entities.EOUnlimitsImage;
import com.brijframework.client.entities.EOUnlimitsImageItem;
import com.brijframework.client.entities.EOUnlimitsTag;
import com.brijframework.client.entities.EOUnlimitsTagItem;
import com.brijframework.client.entities.EOUnlimitsVisualize;
import com.brijframework.client.forgin.model.ClientBoardingAnswer;
import com.brijframework.client.forgin.model.ClientOnBoardingQuestion;
import com.brijframework.client.forgin.model.PromptLibarary;
import com.brijframework.client.forgin.repository.ChatGptClient;
import com.brijframework.client.forgin.repository.OnboardingClient;
import com.brijframework.client.forgin.repository.PromptClient;
import com.brijframework.client.repository.UnlimitsExampleRepository;
import com.brijframework.client.repository.UnlimitsImageRepository;
import com.brijframework.client.repository.UnlimitsTagRepository;
import com.brijframework.client.repository.UnlimitsVisualizeRepository;

@Service
public class DeviceVisualizeServiceImpl extends CrudServiceImpl<UIDeviceUnlimitsVisualize, EOUnlimitsVisualize, Long>
		implements DeviceVisualizeService {

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
	public List<UIDeviceUnlimits> findAllDeviceUnlimits(Map<String, List<String>> headers,
			Map<String, Object> filters) {
		List<UIDeviceUnlimitsTag> custUnlimitsTags = clientUnlimitsTagService.findAll(headers, filters);

		List<UIDeviceUnlimitsImage> custUnlimitsImages = clientUnlimitsImageService.findAll(headers, filters);

		List<UIDeviceUnlimitsExample> custUnlimitsExamples = clientUnlimitsExampleService.findAll(headers, filters);

		return new ArrayList<UIDeviceUnlimits>() {
			/**
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
	public UIDeviceUnlimitsVisualize request(UIDeviceVisualizeRequest clientVisualizeRequest, Map<String, List<String>> headers) {
		switch (clientVisualizeRequest.getType()) {
		case WORDS: {
			return buildVisualizeByWords(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId(), headers);
		}
		case IMAGE: {
			return buildVisualizeByImage(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId(), headers);
		}
		case EXAMPLE: {
			return buildVisualizeByExample(clientVisualizeRequest.getYear(), clientVisualizeRequest.getUnlimitId(), headers);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + clientVisualizeRequest.getType());
		}
	}

	private UIDeviceUnlimitsVisualize buildVisualizeByWords(Integer year, Long unlimitId, Map<String, List<String>> headers) {
		EOUnlimitsTag unlimitsTag = clientUnlimitsTagRepository.findById(unlimitId)
				.orElseThrow(() -> new RuntimeException("Invalid unlimt!"));
		UIDeviceUnlimitsVisualize buildVisualize = buildVisualize(year);
		StringBuffer request = new StringBuffer(buildVisualize.getVisualizeRequest());

		List<EOUnlimitsTagItem> tagItems = unlimitsTag.getTagItems().stream().filter(item -> item.getYear().equals(year)).toList();
		if(!CollectionUtils.isEmpty(tagItems)) {
			Map<Long, List<EOUnlimitsTagItem>> imageItemsBySubCategory = tagItems.stream().collect(Collectors.groupingBy(EOUnlimitsTagItem::getSubCategoryId));
			imageItemsBySubCategory.forEach((subCategoryId, tagItemList) -> {
				PromptLibarary subCategoryPrompt = getPrompt(promptClient.getPromptsBySubCategory(subCategoryId));
				if (subCategoryPrompt != null && StringUtil.isNonEmpty(subCategoryPrompt.getDescription())) {
					request.append(subCategoryPrompt.getDescription());
				}
				for (EOUnlimitsTagItem clientUnlimitsTagItem : tagItemList) {
					String tagName = clientUnlimitsTagItem.getTagName();
					addTagNameFromTag(request, tagName);
				}
			});
		}
		String visualizeRequest = request.toString();
		buildVisualize.setVisualizeRequest(visualizeRequest);
		buildVisualize.setVisualizeResponse(buildCptResponse(visualizeRequest, buildVisualize));
		buildVisualize.setVisualizeYear(year);
		buildVisualize.setEoUnlimits(unlimitsTag);
		buildVisualize.setUnlimitId(unlimitId);
		return add(buildVisualize, headers);
	}
	
	private void preSave(UIDeviceUnlimitsVisualize data, EOUnlimitsVisualize entity) {
		if(data.getEoUnlimits() instanceof EOUnlimitsExample) {
			entity.setUnlimitsExample((EOUnlimitsExample) data.getEoUnlimits());
		}
		if(data.getEoUnlimits() instanceof EOUnlimitsImage) {
			entity.setUnlimitsImage((EOUnlimitsImage) data.getEoUnlimits());
		}
		if(data.getEoUnlimits() instanceof EOUnlimitsTag) {
			entity.setUnlimitsTag((EOUnlimitsTag) data.getEoUnlimits());
		}
	}
	

	private void preSave(UIDeviceUnlimitsVisualize data) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		if(data.getUnlimitId()!=null) {
			switch (data.getType()) {
			case WORDS: {
				unlimitsVisualizeRepository.findOneByUnlimitsTagIdAndVisualizeYear(data.getUnlimitId(), data.getVisualizeYear()).ifPresent(unlimitsVisualize->{
					data.setId(unlimitsVisualize.getId());
				});
				if(data.getEoUnlimits()==null) {
					data.setEoUnlimits(clientUnlimitsTagRepository.getReferenceById(data.getUnlimitId()));
				}
				break;
			}
			case IMAGE: {
				unlimitsVisualizeRepository.findOneByUnlimitsImageIdAndVisualizeYear(data.getUnlimitId(), data.getVisualizeYear()).ifPresent(unlimitsVisualize->{
					data.setId(unlimitsVisualize.getId());
				});
				if(data.getEoUnlimits()==null) {
					data.setEoUnlimits(clientUnlimitsImageRepository.getReferenceById(data.getUnlimitId()));
				}
				break;
			}
			case EXAMPLE: {
				unlimitsVisualizeRepository.findOneByUnlimitsExampleIdAndVisualizeYear(data.getUnlimitId(), data.getVisualizeYear()).ifPresent(unlimitsVisualize->{
					data.setId(unlimitsVisualize.getId());
				});
				if(data.getEoUnlimits()==null) {
					data.setEoUnlimits(clientUnlimitsExampleRepository.getReferenceById(data.getUnlimitId()));
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + data.getType());
			}
		}
	}
	
	
	@Override
	public void preAdd(UIDeviceUnlimitsVisualize data, Map<String, List<String>> headers) {
		preSave(data);
	}
	
	@Override
	public void preAdd(UIDeviceUnlimitsVisualize data, EOUnlimitsVisualize entity, Map<String, List<String>> headers) {
		preSave(data, entity);
	}

	@Override
	public void preUpdate(UIDeviceUnlimitsVisualize data, Map<String, List<String>> headers) {
		preSave(data);
	}

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.add("unlimitsExample");
		ignoreProperties.add("unlimitsImage");
		ignoreProperties.add("unlimitsTag");
		return ignoreProperties;
	}
	
	@Override
	public void preUpdate(UIDeviceUnlimitsVisualize data, EOUnlimitsVisualize entity, Map<String, List<String>> headers) {
		preSave(data, entity);
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

	private UIDeviceUnlimitsVisualize buildVisualizeByImage(Integer year, Long unlimitId, Map<String, List<String>> headers) {
		EOUnlimitsImage unlimitsImage = clientUnlimitsImageRepository.findById(unlimitId)
				.orElseThrow(() -> new RuntimeException("Invalid unlimt!"));
		UIDeviceUnlimitsVisualize buildVisualize = buildVisualize(year);
		StringBuffer request = new StringBuffer(buildVisualize.getVisualizeRequest());

		
		if(!CollectionUtils.isEmpty( unlimitsImage.getImageItems())) {
			List<EOUnlimitsImageItem> imageItems = unlimitsImage.getImageItems().stream()
					.filter(item -> item.getYear().equals(year)).toList();
			Map<Long, List<EOUnlimitsImageItem>> imageItemsBySubCategory = imageItems.stream()
					.collect(Collectors.groupingBy(EOUnlimitsImageItem::getSubCategoryId));
			imageItemsBySubCategory.forEach((subCategoryId, imageItemList) -> {
				PromptLibarary subCategoryPrompt = getPrompt(promptClient.getPromptsBySubCategory(subCategoryId));
				if (subCategoryPrompt != null && StringUtil.isNonEmpty(subCategoryPrompt.getDescription())) {
					request.append(subCategoryPrompt.getDescription());
				}
	
				for (EOUnlimitsImageItem clientUnlimitsImageItem : imageItems) {
					String imageUrl = clientUnlimitsImageItem.getImageUrl();
					addTagNameFromImage(request, imageUrl);
				}
			});
		}
		String visualizeRequest = request.toString();
		buildVisualize.setVisualizeRequest(visualizeRequest);
		buildVisualize.setVisualizeResponse(buildCptResponse(visualizeRequest, buildVisualize));
		buildVisualize.setVisualizeYear(year);
		buildVisualize.setEoUnlimits(unlimitsImage);
		buildVisualize.setUnlimitId(unlimitId);
		return add(buildVisualize, headers);
	}

	private UIDeviceUnlimitsVisualize buildVisualizeByExample(Integer year, Long unlimitId, Map<String, List<String>> headers) {
		EOUnlimitsExample unlimitsExample = clientUnlimitsExampleRepository.findById(unlimitId).orElseThrow(() -> new RuntimeException("Invalid unlimitId!"));
		UIDeviceUnlimitsVisualize buildVisualize = buildVisualize(year);
		List<EOUnlimitsExampleItem> exampleItems = unlimitsExample.getExampleItems();
		StringBuffer request = new StringBuffer(buildVisualize.getVisualizeRequest());
		if(!CollectionUtils.isEmpty(exampleItems)) {
			for (EOUnlimitsExampleItem clientUnlimitsExampleItem : exampleItems) {
	
				String tagName = clientUnlimitsExampleItem.getTagName();
	
				if (addTagNameFromTag(request, tagName)) {
					continue;
				}
	
				String imageUrl = clientUnlimitsExampleItem.getImageUrl();
				addTagNameFromImage(request, imageUrl);
			}
		}
		String visualizeRequest = request.toString();
		buildVisualize.setVisualizeRequest(visualizeRequest);
		buildVisualize.setVisualizeResponse(buildCptResponse(visualizeRequest, buildVisualize));
		buildVisualize.setVisualizeYear(year);
		buildVisualize.setEoUnlimits(unlimitsExample);
		buildVisualize.setUnlimitId(unlimitId);
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
		PromptLibarary yearPrompt = getPrompt(promptClient.getPromptsByYear(year));
		StringBuffer request = new StringBuffer();
		if (yearPrompt != null && StringUtil.isNonEmpty(yearPrompt.getDescription())) {
			request.append(yearPrompt.getDescription());
		}
		List<ClientOnBoardingQuestion> onboardings = getBoardingQuestion(onboardingClient.getOnboardings());
		if (!CollectionUtils.isEmpty(onboardings)) {
			onboardings.sort((q1, q2) -> q1.getQuestion().getOrderSequence().compareTo(q1.getQuestion().getOrderSequence()));
			for (ClientOnBoardingQuestion clientOnBoardingQuestion : onboardings) {
				if (CollectionUtils.isEmpty(clientOnBoardingQuestion.getAnswers())) {
					continue;
				}
				request.append(clientOnBoardingQuestion.getQuestion().getQuestion() + " is ");
				for (ClientBoardingAnswer answer : clientOnBoardingQuestion.getAnswers()) {
					request.append(answer.getValue() + ".");
				}
			}
		}
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.YEAR, year);
		UIDeviceUnlimitsVisualize uiVisualize = new UIDeviceUnlimitsVisualize();
		uiVisualize.setVisualizeDate(DateUtil.getDateStringForPattern(instance.getTime(), Constants.DEVICE_DATE_FORMAT_MMMM_DD_YYYY));
		uiVisualize.setVisualizeRequest(request.toString());
		return uiVisualize;
	}

	private PromptLibarary getPrompt(Response<List<PromptLibarary>> response) {
		if ("1".equals(response.getSuccess())) {
			List<PromptLibarary> data = response.getData();
			if (CollectionUtils.isEmpty(data)) {
				return null;
			}
			return data.get(0);
		}
		return null;
	}

	private List<ClientOnBoardingQuestion> getBoardingQuestion(Response<List<ClientOnBoardingQuestion>> response) {
		if ("1".equals(response.getSuccess())) {
			return response.getData();
		}
		return null;
	}

}
