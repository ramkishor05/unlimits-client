package com.brijframework.client.unlimits.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.client.repository.ClientAffirmationGroupRepository;
import com.brijframework.client.repository.ClientGoalGroupRepository;
import com.brijframework.client.repository.ClientMindSetGroupRepository;
import com.brijframework.client.repository.ClientReProgramGroupRepository;
import com.brijframework.client.repository.ClientUnlimitsExampleRepository;
import com.brijframework.client.repository.ClientUnlimitsImageRepository;
import com.brijframework.client.repository.CustUnlimitsTagRepository;
import com.brijframework.client.unlimits.model.UIGlobalDashboard;

@Service
public class GlobalClientDashboardServiceImpl implements GlobalCustDashboardService {

	@Autowired
	private CustUnlimitsTagRepository clientUnlimitsTagRepository;
	
	@Autowired
	private ClientUnlimitsImageRepository clientUnlimitsImageRepository;
	
	@Autowired
	private ClientUnlimitsExampleRepository clientUnlimitsExampleRepository;
	
	@Autowired
	private ClientGoalGroupRepository clientGoalRepository;
	
	@Autowired
	private ClientMindSetGroupRepository clientMindSetRepository;
	
	@Autowired
	private ClientReProgramGroupRepository clientReProgramRepository;
	
	@Autowired
	private ClientAffirmationGroupRepository clientAffirmationRepository;
	
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
