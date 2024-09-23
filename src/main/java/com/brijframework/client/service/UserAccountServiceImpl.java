package com.brijframework.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.forgin.model.UIUserAccount;
import com.brijframework.client.forgin.repository.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	private UserAccountRepository userLoginRepository;

	@Override
	public UIUserAccount loadUserByUsername(String token) throws UsernameNotFoundException {
		UIUserAccount findUserLogin = userLoginRepository.findByToken(token);
		if (findUserLogin==null) {
			throw new UserNotFoundException();
		}
		return findUserLogin;
	}

}
