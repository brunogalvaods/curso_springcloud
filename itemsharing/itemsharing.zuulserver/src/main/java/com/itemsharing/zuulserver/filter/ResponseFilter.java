package com.itemsharing.zuulserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ResponseFilter extends ZuulFilter {

private static final Logger LOG = LoggerFactory.getLogger(ResponseFilter.class);
	
	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	
	@Autowired
	private FilterUtils filterUtils;
	
	@Override
	public Object run() {
		RequestContext cxt = RequestContext.getCurrentContext();
		
		LOG.debug("Adding the correlation id to the outbound headers {}", filterUtils.getCorrelationId());
		cxt.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());
		
		LOG.debug("Completing outgonig request for {}", cxt.getRequest().getRequestURI());
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	@Override
	public String filterType() {
		return FilterUtils.POST_FILTER_TYPE;
	}

}
