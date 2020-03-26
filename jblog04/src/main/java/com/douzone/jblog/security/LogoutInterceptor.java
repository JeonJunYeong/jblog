package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		
		System.out.println("URL :"+ request.getRequestURL());
		
		String[] arr=request.getRequestURL().toString().split("/");
		
		String now = arr[arr.length-1];
		
		
		System.out.println(now);
		
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		
		if(!now.equals("logout")) {
			response.sendRedirect(request.getContextPath()+"/"+now);
		}else {
			response.sendRedirect(request.getContextPath());
		}
			
		return false;
	}

}
