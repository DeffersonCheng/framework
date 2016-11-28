package com.wdl.webapp.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author bin
 *
 */
public class SystemFilter implements Filter {

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response =(HttpServletResponse) servletResponse;
		String basePath = request.getContextPath();
		request.setAttribute("basePath", basePath);
		// XSS漏洞
		filterChain.doFilter(new RequestWrapper(request), response);
		//禁用缓存 2016年7月19日14:28:09 @Defferson.Cheng
		response.setHeader("Pragma","No-cache");
	    response.setHeader("Cache-Control","no-cache");
	    response.setDateHeader("Expires", 0);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {

	}
}
