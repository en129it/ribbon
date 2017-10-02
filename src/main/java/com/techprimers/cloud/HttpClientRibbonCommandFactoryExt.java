package com.techprimers.cloud;

import java.util.Set;

import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.ribbon.apache.RibbonLoadBalancingHttpClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandContext;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.apache.HttpClientRibbonCommand;
import org.springframework.cloud.netflix.zuul.filters.route.apache.HttpClientRibbonCommandFactory;

public class HttpClientRibbonCommandFactoryExt extends HttpClientRibbonCommandFactory {

	private final SpringClientFactory clientFactory;
	private final ZuulProperties zuulProperties;
	
	
	public HttpClientRibbonCommandFactoryExt(SpringClientFactory clientFactory, ZuulProperties zuulProperties) {
		super(clientFactory, zuulProperties);
		this.clientFactory = clientFactory;
		this.zuulProperties = zuulProperties;
	}

	public HttpClientRibbonCommandFactoryExt(SpringClientFactory clientFactory, ZuulProperties zuulProperties, Set<ZuulFallbackProvider> fallbackProviders) {
		super(clientFactory, zuulProperties, fallbackProviders);
		this.clientFactory = clientFactory;
		this.zuulProperties = zuulProperties;
	}

	@Override
	protected ZuulFallbackProvider getFallbackProvider(String route) {
		return super.getFallbackProvider(route);
	}
	
	@Override
	public HttpClientRibbonCommand create(RibbonCommandContext context) {
		ZuulFallbackProvider zuulFallbackProvider = getFallbackProvider(context.getServiceId());
		final String serviceId = context.getServiceId();
		final RibbonLoadBalancingHttpClientExt client = this.clientFactory.getClient(
				serviceId, RibbonLoadBalancingHttpClientExt.class);
		client.setLoadBalancer(this.clientFactory.getLoadBalancer(serviceId));

		return new HttpClientRibbonCommand(serviceId, client, context, zuulProperties, zuulFallbackProvider,
				clientFactory.getClientConfig(serviceId));
	}
	
	
}
