package com.brijframework.client.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.brijframework.client.forgin.model.UIUserAccount;

public interface UserAccountService extends UserDetailsService {

	@Override
	UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException;

}
