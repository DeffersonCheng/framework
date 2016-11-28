package com.wdl.webapp.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import com.wdl.util.PatternUtil;

/**
 * @author bin
 */
public class SecaurityFilter implements Filter {
	private static String[] ignoreUrls = 
			new String[] { "/userLogin/*", "/resources/*" };
//	private static final Logger LOGGER = Logger.getLogger(SecaurityFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// if (httpServletRequest.getSession().getAttribute("user") == null) {
		if (!SecurityUtils.getSubject().isAuthenticated() && !isIgnoreUrl(httpServletRequest.getServletPath())) {
			// 判断session里是否有用户信息
			if (httpServletRequest.getHeader("x-requested-with") != null
					&& httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				// 如果是ajax请求响应头会有，x-requested-with
				// 在响应头设置session状态
				httpServletResponse.setHeader("systemStatus", "timeout");
				return;
			}
		} else if ("POST".equals(httpServletRequest.getMethod()) && httpServletRequest.getHeader("csrfToken") == null
				&& httpServletRequest.getParameter("csrfToken") == null
				&& !isIgnoreUrl(httpServletRequest.getServletPath())) {
			httpServletResponse.setHeader("systemStatus", "csrf");
			return;
		}
		chain.doFilter(request, response);
	}


	@Override
	public void destroy() {

	}

	private static boolean checkUrlInSet(String url, Set<String> set) {
		boolean flag = false;
		for (String ignore : set) {
			if (PatternUtil.matchUrl(ignore, url)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	private static boolean isIgnoreUrl(String url) {
		boolean flag = false;
		for (String ignore : ignoreUrls) {
			if (PatternUtil.matchUrl(ignore, url)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

}