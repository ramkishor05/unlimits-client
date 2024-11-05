package com.brijframework.client.global.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.forgin.model.MainCategoryModel;
import com.brijframework.client.forgin.model.SubCategoryModel;
import com.brijframework.client.forgin.repository.MainCategoryClient;
import com.brijframework.client.forgin.repository.SubCategoryClient;
import com.brijframework.client.global.model.UIGlobalDashboard;
import com.brijframework.client.global.model.UIGlobalDashboardCategoryWiseUnlimits;
import com.brijframework.client.repository.AffirmationGroupRepository;
import com.brijframework.client.repository.GoalGroupRepository;
import com.brijframework.client.repository.MindSetGroupRepository;
import com.brijframework.client.repository.ReProgramGroupRepository;
import com.brijframework.client.repository.UnlimitsRepository;
import com.brijframework.client.repository.projections.UnlimtsProjecttion;

@Service
public class GlobalDashboardServiceImpl implements GlobalDashboardService {

	
	@Autowired
	private UnlimitsRepository clientUnlimitsRepository;
	
	@Autowired
	private GoalGroupRepository clientGoalRepository;
	
	@Autowired
	private MindSetGroupRepository clientMindSetRepository;
	
	@Autowired
	private ReProgramGroupRepository clientReProgramRepository;
	
	@Autowired
	private AffirmationGroupRepository clientAffirmationRepository;
	
	@Autowired
	private SubCategoryClient subCategoryClient;
	
	@Autowired
	private MainCategoryClient mainCategoryClient;
	
	@Override
	public UIGlobalDashboard getDashboard() {
		
		long totalUnlimits = clientUnlimitsRepository.count();
		
		UIGlobalDashboard clientDashboard=new UIGlobalDashboard();
		
		clientDashboard.setTotalUnlimits(totalUnlimits);
		
		clientDashboard.setTotalAffirmations(clientAffirmationRepository.count());
		
		clientDashboard.setTotalGoals(clientGoalRepository.count());
		
		clientDashboard.setTotalMindSets(clientMindSetRepository.count());
		
		clientDashboard.setTotalReprograms(clientReProgramRepository.count());
		
		List<UnlimtsProjecttion> findUnlimits = clientUnlimitsRepository.findUnlimits(RecordStatus.ACTIVETED.getStatusIds());
		
		Map<String, MainCategoryModel> mainCategoryModelMap = getMainCategoryModelList(mainCategoryClient.getMainCategoryModelList()).stream().collect(Collectors.toMap(MainCategoryModel::getName, Function.identity()));
		
		Map<String, SubCategoryModel> subCategoryModelMap = getSubCategoryModelList(subCategoryClient.getSubCategoryModelList()).stream().collect(Collectors.toMap(SubCategoryModel::getName, Function.identity()));
		
		Collection<UIGlobalDashboardCategoryWiseUnlimits> totalUnlimitsBySubCategory = buildCountTagUnlimitsBySubCategory(mainCategoryModelMap, subCategoryModelMap, findUnlimits);
		
		clientDashboard.setTotalUnlimitsBySubCategory(totalUnlimitsBySubCategory);
		
		Collection<UIGlobalDashboardCategoryWiseUnlimits> totalUnlimitsByMainCategory = buildCountTagUnlimitsByMainCategory(mainCategoryModelMap, findUnlimits);
		
		clientDashboard.setTotalUnlimitsByMainCategory(totalUnlimitsByMainCategory);
		
		return clientDashboard;
	}

	private Collection<UIGlobalDashboardCategoryWiseUnlimits> buildCountTagUnlimitsBySubCategory(Map<String, MainCategoryModel> mainCategoryModelMap, Map<String, SubCategoryModel> subCategoryModelMap, List<UnlimtsProjecttion> findUnlimits) {
		Map<String, UIGlobalDashboardCategoryWiseUnlimits> totalUnlimitsBySubCategory=new HashMap<String, UIGlobalDashboardCategoryWiseUnlimits>();
		
		findUnlimits.stream().filter(item->StringUtil.isNonEmpty(item.getMainCategoryName()) && StringUtil.isNonEmpty(item.getSubCategoryName())).collect(Collectors.groupingBy(UnlimtsProjecttion::getSubCategoryName)).forEach((subCategoryName,list)->{
			UnlimtsProjecttion data = list.get(0);
			int size = list.stream().collect(Collectors.groupingBy(UnlimtsProjecttion::getUnlimitsId)).keySet().size();
			UIGlobalDashboardCategoryWiseUnlimits dashboardCategoryWiseUnlimits=totalUnlimitsBySubCategory.get(subCategoryName);
			SubCategoryModel subCategoryModel = subCategoryModelMap.getOrDefault(subCategoryName, new SubCategoryModel());
			if(dashboardCategoryWiseUnlimits==null) {
				UIGlobalDashboardCategoryWiseUnlimits uiGlobalDashboardCategoryWiseUnlimits = new UIGlobalDashboardCategoryWiseUnlimits();
				BeanUtils.copyProperties(data, uiGlobalDashboardCategoryWiseUnlimits);
				uiGlobalDashboardCategoryWiseUnlimits.setColor(subCategoryModel.getColor());
				uiGlobalDashboardCategoryWiseUnlimits.addCount(Long.valueOf(size));
				totalUnlimitsBySubCategory.put(data.getSubCategoryName(), uiGlobalDashboardCategoryWiseUnlimits);
			} else {
				dashboardCategoryWiseUnlimits.addCount(Long.valueOf(size));
			}
		});
		
		subCategoryModelMap.forEach((subCategoryName, subCategoryModelValue)->{
			UIGlobalDashboardCategoryWiseUnlimits dashboardCategoryWiseUnlimits=totalUnlimitsBySubCategory.get(subCategoryName);
			if(dashboardCategoryWiseUnlimits==null) {
				UIGlobalDashboardCategoryWiseUnlimits uiGlobalDashboardCategoryWiseUnlimits = new UIGlobalDashboardCategoryWiseUnlimits();
				uiGlobalDashboardCategoryWiseUnlimits.setMainCategoryId(subCategoryModelValue.getMainCategoryId());
				uiGlobalDashboardCategoryWiseUnlimits.setMainCategoryName(subCategoryModelValue.getMainCategoryName());
				uiGlobalDashboardCategoryWiseUnlimits.setSubCategoryId(subCategoryModelValue.getId());
				uiGlobalDashboardCategoryWiseUnlimits.setSubCategoryName(subCategoryModelValue.getName());
				uiGlobalDashboardCategoryWiseUnlimits.setColor(subCategoryModelValue.getColor());
				uiGlobalDashboardCategoryWiseUnlimits.setUnlimitsCount(0l);
				totalUnlimitsBySubCategory.put(subCategoryName, uiGlobalDashboardCategoryWiseUnlimits);
			}
		});
		
		return totalUnlimitsBySubCategory.values().stream().sorted((s1,s2)->{
			int order=s1.getMainCategoryId().compareTo(s2.getMainCategoryId());
			if(order!=0) {
				return order;
			}
			return s1.getSubCategoryId().compareTo(s2.getSubCategoryId());
		}).toList();
	}
	
	private Collection<UIGlobalDashboardCategoryWiseUnlimits> buildCountTagUnlimitsByMainCategory(Map<String, MainCategoryModel> mainCategoryModelMap, List<UnlimtsProjecttion> findUnlimits) {
		
		Map<String, UIGlobalDashboardCategoryWiseUnlimits> totalUnlimitsByMainCategory=new HashMap<String, UIGlobalDashboardCategoryWiseUnlimits>();
		
		findUnlimits.stream().filter(item->StringUtil.isNonEmpty(item.getMainCategoryName()) && StringUtil.isNonEmpty(item.getSubCategoryName())).collect(Collectors.groupingBy(UnlimtsProjecttion::getMainCategoryName)).forEach((mainCategoryName,list)->{
			UnlimtsProjecttion data = list.get(0);
			int size = list.stream().collect(Collectors.groupingBy(UnlimtsProjecttion::getUnlimitsId)).keySet().size();
			UIGlobalDashboardCategoryWiseUnlimits dashboardCategoryWiseUnlimits=totalUnlimitsByMainCategory.get(mainCategoryName);
			MainCategoryModel mainCategoryModel = mainCategoryModelMap.getOrDefault(mainCategoryName, new MainCategoryModel());
			if(dashboardCategoryWiseUnlimits==null) {
				UIGlobalDashboardCategoryWiseUnlimits uiGlobalDashboardCategoryWiseUnlimits = new UIGlobalDashboardCategoryWiseUnlimits();
				BeanUtils.copyProperties(data, uiGlobalDashboardCategoryWiseUnlimits);
				uiGlobalDashboardCategoryWiseUnlimits.setColor(mainCategoryModel.getColor());
				uiGlobalDashboardCategoryWiseUnlimits.addCount(Long.valueOf(size));
				totalUnlimitsByMainCategory.put(mainCategoryName, uiGlobalDashboardCategoryWiseUnlimits);
			} else {
				dashboardCategoryWiseUnlimits.addCount(Long.valueOf(size));
			}
		});
		
		mainCategoryModelMap.forEach((mainCategoryName, mainCategoryModelValue)->{
			UIGlobalDashboardCategoryWiseUnlimits dashboardCategoryWiseUnlimits=totalUnlimitsByMainCategory.get(mainCategoryName);
			if(dashboardCategoryWiseUnlimits==null) {
				UIGlobalDashboardCategoryWiseUnlimits uiGlobalDashboardCategoryWiseUnlimits = new UIGlobalDashboardCategoryWiseUnlimits();
				uiGlobalDashboardCategoryWiseUnlimits.setColor(mainCategoryModelValue.getColor());
				uiGlobalDashboardCategoryWiseUnlimits.setMainCategoryId(mainCategoryModelValue.getId());
				uiGlobalDashboardCategoryWiseUnlimits.setMainCategoryName(mainCategoryModelValue.getName());
				uiGlobalDashboardCategoryWiseUnlimits.setUnlimitsCount(0l);
				totalUnlimitsByMainCategory.put(mainCategoryName, uiGlobalDashboardCategoryWiseUnlimits);
			}
		});
		
		return totalUnlimitsByMainCategory.values().stream().sorted((s1,s2)->{
			return s1.getMainCategoryId().compareTo(s2.getMainCategoryId());
		}).toList();
	}

	private List<MainCategoryModel> getMainCategoryModelList(
			Response<List<MainCategoryModel>> response) {
		if(response!=null && "1".equals(response.getSuccess())) {
			return response.getData();
		}
		return new ArrayList<MainCategoryModel>();
	}
	
	private List<SubCategoryModel> getSubCategoryModelList(
			Response<List<SubCategoryModel>> response) {
		if(response!=null && "1".equals(response.getSuccess())) {
			return response.getData();
		}
		return new ArrayList<SubCategoryModel>();
	}

}
