package com.techprimers.cloud;

import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpRequest;
import org.springframework.cloud.netflix.ribbon.apache.RibbonLoadBalancingHttpClient;

import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.config.IClientConfig;

public class RibbonLoadBalancingHttpClientExt extends RibbonLoadBalancingHttpClient {

	@Override
	public RequestSpecificRetryHandler getRequestSpecificRetryHandler(RibbonApacheHttpRequest request, IClientConfig requestConfig) {
		return new RequestSpecificRetryHandler(true, true, this.getRetryHandler(), requestConfig);
	}
	
}
