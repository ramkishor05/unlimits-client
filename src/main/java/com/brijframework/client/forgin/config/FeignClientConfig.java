package com.brijframework.client.forgin.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unlimits.rest.context.ApiTokenContext;

import feign.RequestInterceptor;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor bearerAuthRequestInterceptor() {
        return requestTemplate -> {
        	requestTemplate.header("Authorization", ApiTokenContext.getContext().getCurrentToken());
        };
    }
}