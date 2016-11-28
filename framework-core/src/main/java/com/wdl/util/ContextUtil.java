package com.wdl.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目工具类
 * @author bin
 *
 */
public class ContextUtil {
	/**
	 * 获取项目全路径
	 * @param request
	 * @return
	 */
	private static String webUrl=ResourcesUtil.getResourcesValue("/webconfig.properties","com.experian.request.webUrl"); 
	public static String getLocalUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}
	public static String getWebUrl() {
		return webUrl;
	}
	/**
	 * 获取项目
	 * @param request
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request) {
		return request.getRealPath("/");
	}
	public static String getContextPath(HttpServletRequest request){
		return request.getContextPath();
	}
	
	public static String getInternetResourceUrl(){
		return ResourcesUtil.getResourcesValue("/webconfig.properties","internet.resource.url");
	}
	
	public static String getLocalResourceUrl(){
		return ResourcesUtil.getResourcesValue("/webconfig.properties","local.resource.url");
	}
	
	
}
