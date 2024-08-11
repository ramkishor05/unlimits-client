package com.brijframework.client.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.client.global.model.UIGlobalDashboard;
import com.brijframework.client.repository.AffirmationGroupRepository;
import com.brijframework.client.repository.GoalGroupRepository;
import com.brijframework.client.repository.MindSetGroupRepository;
import com.brijframework.client.repository.ReProgramGroupRepository;
import com.brijframework.client.repository.UnlimitsExampleRepository;
import com.brijframework.client.repository.UnlimitsImageRepository;
import com.brijframework.client.repository.UnlimitsTagRepository;

@Service
public class GlobalDashboardServiceImpl implements GlobalDashboardService {

	@Autowired
	private UnlimitsTagRepository clientUnlimitsTagRepository;
	
	@Autowired
	private UnlimitsImageRepository clientUnlimitsImageRepository;
	
	@Autowired
	private UnlimitsExampleRepository clientUnlimitsExampleRepository;
	
	@Autowired
	private GoalGroupRepository clientGoalRepository;
	
	@Autowired
	private MindSetGroupRepository clientMindSetRepository;
	
	@Autowired
	private ReProgramGroupRepository clientReProgramRepository;
	
	@Autowired
	private AffirmationGroupRepository clientAffirmationRepository;
	
	@Override
	public UIGlobalDashboard getDashboard() {
		
		long countTag = clientUnlimitsTagRepository.count();
		
		long countImage = clientUnlimitsImageRepository.count();
		
		long countExample = clientUnlimitsExampleRepository.count();
		
		UIGlobalDashboard clientDashboard=new UIGlobalDashboard();
		
		clientDashboard.setTotalUnlimits(countTag+countImage+countExample);
		
		clientDashboard.setTotalAffirmations(clientAffirmationRepository.count());
		
		clientDashboard.setTotalGoals(clientGoalRepository.count());
		
		clientDashboard.setTotalMindSets(clientMindSetRepository.count());
		
		clientDashboard.setTotalReprograms(clientReProgramRepository.count());
		
		return clientDashboard;
	}

}
