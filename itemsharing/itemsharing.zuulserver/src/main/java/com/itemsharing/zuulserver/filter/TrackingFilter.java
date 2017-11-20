package com.itemsharing.zuulserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class TrackingFilter extends  ZuulFilter {

	private static final Logger LOG = LoggerFactory.getLogger(TrackingFilter.class);
	
	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	
	@Autowired
	private FilterUtils filterUtils;
	
	@Override
	public Object run() {
		if(isCorrelationIdPresent()) {
			LOG.debug("is-correlation-id found in tracking filter: {}", filterUtils.getCorrelationId());
		} else {
			filterUtils.setCorrelationId(generateCorrelationId());
			LOG.debug("is-correlation-id generated in tracking filter: {}", filterUtils.getCorrelationId());
		}
		
		RequestContext cxt = RequestContext.getCurrentContext();
		LOG.debug("Processing incoming request for {}", cxt.getRequest().getRequestURI());
		
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
		return FilterUtils.PRE_FILTER_TYPE;
	}
	
	private boolean isCorrelationIdPresent() {
		if(filterUtils.getCorrelationId() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}

}
