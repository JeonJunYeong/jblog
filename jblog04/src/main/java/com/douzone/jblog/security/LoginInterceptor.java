package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;


public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String blog = request.getParameter("blog");
		String now = request.getParameter("now");

		System.out.println("URL : "+request.getRequestURL());
		
		UserVo vo = new UserVo();
		vo.setId(id);
		vo.setPassword(password);

		UserVo authUser = userService.getUser(vo);
		
		if(authUser == null) {
			request.setAttribute("userVo",vo );
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
			.forward(request, response);
			
			
			return false;
		}
		
		System.out.println("------>authUser:" + authUser);
		if(blog==null) {
			blog="";
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		if(blog.equals("blog")) {
			response.sendRedirect(request.getContextPath()+"/"+now);
		}else {
			response.sendRedirect(request.getContextPath());
		}
	
		
		return false;
	}

}
