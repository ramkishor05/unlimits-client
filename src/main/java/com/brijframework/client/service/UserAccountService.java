package com.brijframework.client.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.brijframework.client.forgin.model.UserAccountModel;

public interface UserAccountService extends UserDetailsService {

	@Override
	UserAccountModel loadUserByUsername(String username) throws UsernameNotFoundException;

}
