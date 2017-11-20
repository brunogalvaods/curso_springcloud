package com.itemsharing.zuulserver.filter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;

@Component
public class FilterUtils {

	public static final String CORRELATION_ID = "is-correlation-id";
	public static final String AUTH_TOKEN = "Authorization";
	public static final String USER_ID = "is-user-id";
	public static final String PRE_FILTER_TYPE = "pre";
	public static final String POST_FILTER_TYPE = "post";
	public static final String ROUTER_FILTER_TYPE = "route";
	
	public String getCorrelationId() {
		RequestContext cxt = RequestContext.getCurrentContext();
		
		if(cxt.getRequest().getHeader(CORRELATION_ID) != null) {
			return cxt.getRequest().getHeader(CORRELATION_ID);
		} else {
			return cxt.getZuulRequestHeaders().get(CORRELATION_ID);
		}
	}
	
	public void setCorrelationId(String correlationId) {
		RequestContext cxt = RequestContext.getCurrentContext();
		cxt.addZuulRequestHeader(CORRELATION_ID, correlationId);
	}
	
	public final String getUserId() {
		RequestContext cxt = RequestContext.getCurrentContext();
		
		if(cxt.getRequest().getHeader(USER_ID) != null) {
			return cxt.getRequest().getHeader(USER_ID);
		} else {
			return cxt.getZuulRequestHeaders().get(USER_ID);
		}
	}
	
	public void setUserId(String userId) {
		RequestContext cxt = RequestContext.getCurrentContext();
		cxt.addZuulRequestHeader(USER_ID, userId);
	}
	
	public final String getAuthToken() {
		RequestContext cxt = RequestContext.getCurrentContext();
		return cxt.getRequest().getHeader(AUTH_TOKEN);
	}
	
}
