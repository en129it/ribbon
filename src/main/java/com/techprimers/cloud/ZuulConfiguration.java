package com.techprimers.cloud;

import java.util.HashSet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryPolicyFactory;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryPolicyFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.apache.HttpClientRibbonCommandFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 https://github.com/TechPrimers/spring-cloud-eureka-hystrix-zuul-example
https://piotrminkowski.wordpress.com/2017/05/15/part-3-creating-microservices-circuit-breaker-fallback-and-load-balancing-with-spring-cloud/

http://localhost:8079/api/server/rest/hello/server

http://localhost:8079/api/client/rest/hello/client
 *
 */

@Configuration
public class ZuulConfiguration {

	@Bean
	public RibbonCommandFactory<?> ribbonCommandFactory(SpringClientFactory clientFactory, ZuulProperties zuulProperties) {
		return new HttpClientRibbonCommandFactoryExt(clientFactory, zuulProperties, new HashSet<>());
	}	

	@Bean
	public LoadBalancedRetryPolicyFactory loadBalancedRetryPolicyFactory(SpringClientFactory clientFactory) {
		return new RibbonLoadBalancedRetryPolicyFactory(clientFactory);
	}
	
}
