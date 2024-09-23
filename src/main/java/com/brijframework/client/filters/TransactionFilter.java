package com.brijframework.client.filters;

import static com.brijframework.client.constants.Constants.APP_ID_KEY;
import static com.brijframework.client.constants.Constants.BUSINESS_ID_KEY;
import static com.brijframework.client.constants.Constants.CUST_APP_ID;
import static com.brijframework.client.constants.Constants.OWNER_ID_KEY;
import static com.brijframework.client.constants.Constants.USER_ROLE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.context.ApiTokenContext;

import com.brijframework.client.constants.Constants;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.util.CommanUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(0)
public class TransactionFilter implements Filter {
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        TransactionRequest requestWrapper = new TransactionRequest(req);
        String apiToken = req.getHeader(Constants.AUTHORIZATION);
        if(StringUtil.isNonEmpty(apiToken)) {
        	ApiTokenContext.getContext().setCurrentToken(apiToken);
        	String userRole = ApiTokenContext.getUserRole(apiToken); 
        	String username = ApiTokenContext.getUsername(apiToken); 
        	requestWrapper.setAttribute(USER_ROLE, userRole);
	        requestWrapper.putHeader(USER_ROLE, userRole);
	        String ownerId = req.getHeader(OWNER_ID_KEY);
	        if(StringUtil.isEmpty(ownerId)) {
	        	ownerId=ApiTokenContext.getUserId(apiToken);
	        }
	        String appId = req.getHeader(APP_ID_KEY);
	        String businessId = req.getHeader(BUSINESS_ID_KEY);
	        if(StringUtil.isNonEmpty(ownerId)&& CommanUtil.isNumeric(ownerId) && StringUtil.isNonEmpty(businessId) && CommanUtil.isNumeric(businessId) && StringUtil.isNonEmpty(appId) && CommanUtil.isNumeric(appId)) {
	        	EOCustBusinessApp custBusinessApp =custBusinessAppRepository.findByCustIdAndAppIdAndBusinessId(Long.valueOf(ownerId), Long.valueOf(appId),Long.valueOf(businessId)).orElse(new EOCustBusinessApp(Long.valueOf(appId), Long.valueOf(ownerId), Long.valueOf(businessId)));
	        	EOCustBusinessApp eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
	        	requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	    		req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	    		ApiSecurityContext.getContext().setCurrentAccount(eoCustBusinessApp);
	        } else  if(StringUtil.isNonEmpty(ownerId) && CommanUtil.isNumeric(ownerId) && StringUtil.isNonEmpty(businessId)&& CommanUtil.isNumeric(businessId)) {
	         	EOCustBusinessApp custBusinessApp = custBusinessAppRepository.findByCustIdAndAppIdAndBusinessId(Long.valueOf(ownerId), Long.valueOf(1l),Long.valueOf(businessId)).orElse(new EOCustBusinessApp(Long.valueOf(1l), Long.valueOf(ownerId), Long.valueOf(businessId)));
	         	EOCustBusinessApp eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
	     		requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	     		req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	     		ApiSecurityContext.getContext().setCurrentAccount(eoCustBusinessApp);
	         } else  if(StringUtil.isNonEmpty(ownerId)&& CommanUtil.isNumeric(ownerId)) {
	        	List<EOCustBusinessApp> custBusinessAppList = custBusinessAppRepository.findByCustIdAndAppId(Long.valueOf(ownerId), Long.valueOf(1l)).orElse(Arrays.asList(new EOCustBusinessApp(Long.valueOf(1l), Long.valueOf(ownerId), Long.valueOf(1l))));
	        	for(EOCustBusinessApp custBusinessApp : custBusinessAppList) {
	      			EOCustBusinessApp eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
	         		requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	         		req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	         		ApiSecurityContext.getContext().setCurrentAccount(eoCustBusinessApp);
	      		}
	        	if(CollectionUtils.isEmpty(custBusinessAppList)) {
	        		EOCustBusinessApp custBusinessApp = new EOCustBusinessApp(Long.valueOf(1l), Long.valueOf(ownerId), Long.valueOf(1l));
	        		EOCustBusinessApp eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
	         		requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	         		req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	         		ApiSecurityContext.getContext().setCurrentAccount(eoCustBusinessApp);
	        	}
	         }
	        if (SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
						getGrantedAuthority(userRole));
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
        }
        chain.doFilter(requestWrapper, response);
    }
    

    private List<GrantedAuthority> getGrantedAuthority(String authority) {
		return Arrays.asList(new GrantedAuthority() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return authority;
			}
		});
	}
}