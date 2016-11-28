package com.wdl.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebContextThreadBind {
	private static ThreadLocal<HttpSession> sessionLocal=new ThreadLocal<HttpSession>();
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();
	public static void setCurrentSession(HttpSession session){
		sessionLocal.set(session);
	}
	public static void setCurrentRequest(HttpServletRequest request){
		requestLocal.set(request);
	}
	public static void setCurrentResponse(HttpServletResponse response){
		responseLocal.set(response);
	}
	public static HttpSession getCurrentSession(){return sessionLocal.get();}
	public static HttpServletRequest getCurrentRequest(){return requestLocal.get();}
	public static HttpServletResponse getCurrentResponse(){return responseLocal.get();}
}
