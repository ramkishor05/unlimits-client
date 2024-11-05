package com.brijframework.client.adptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.brijframework.client.constants.Authority;
import com.brijframework.client.forgin.model.UserAccountModel;
import com.brijframework.client.service.UserAccountService;


@Component
public class TokenAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void doAfterPropertiesSet() {
		
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("AuthProvider :: authenticate() started");
		UserAccountService userDetailsService=userAccountService;
		this.setPasswordEncoder(passwordEncoder);
		this.setUserDetailsService(userDetailsService);
		System.out.println("AuthProvider :: authenticate() end");
		return super.authenticate(authentication);
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		super.additionalAuthenticationChecks(userDetails, authentication);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public UserAccountModel loadUserByUsername(String username, String authority) {
		UserAccountService userDetailsService=null; 
		if(authority.equalsIgnoreCase(Authority.ADMIN.toString())) {
			userDetailsService=userAccountService;
		}
		if(authority.equalsIgnoreCase(Authority.DEVELOPER.toString())) {
			userDetailsService=userAccountService;
		}
		if(authority.equalsIgnoreCase(Authority.USER.toString())) {
			userDetailsService=userAccountService;
		}
		return userDetailsService.loadUserByUsername(username);
	}
	
}