package com.brijframework.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.forgin.model.UserAccountModel;
import com.brijframework.client.forgin.repository.UserClient;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	private UserClient userClient;

	@Override
	public UserAccountModel loadUserByUsername(String token) throws UsernameNotFoundException {
		UserAccountModel findUserLogin = userClient.findByToken(token);
		if (findUserLogin==null) {
			throw new UserNotFoundException();
		}
		return findUserLogin;
	}

}
