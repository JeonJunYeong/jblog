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
		
		String blog = request.getParameter("blog");
		String now = request.getParameter("now");
		
		
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		if(blog.equals("blog")) {
			response.sendRedirect(request.getContextPath()+"/"+now);
		}else {
			response.sendRedirect(request.getContextPath());
		}
			
		return false;
	}

}
