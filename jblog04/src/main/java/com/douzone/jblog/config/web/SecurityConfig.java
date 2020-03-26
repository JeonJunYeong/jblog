package com.douzone.jblog.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.douzone.jblog.security.AuthInterceptor;
import com.douzone.jblog.security.AuthUserHandlerMethodArgumentResolver;
import com.douzone.jblog.security.LoginInterceptor;
import com.douzone.jblog.security.LogoutInterceptor;


@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter{
	
	//Argument Resolver 
	@Bean
	public AuthUserHandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		
		return new AuthUserHandlerMethodArgumentResolver();
	}

	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}
	
	// Interceptors 
	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public LogoutInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor()).addPathPatterns("/user/auth").addPathPatterns("/user/blogauth");
		
		registry.addInterceptor(logoutInterceptor()).addPathPatterns("/user/logout").addPathPatterns("/user/bloglogout/**");
		
		registry.addInterceptor(authInterceptor()).addPathPatterns("/**")
		.excludePathPatterns("/user/logout")
		.excludePathPatterns("/user/**")
		.excludePathPatterns("/blog/auth")
		.excludePathPatterns("/blog/logout")
		.excludePathPatterns("/assets/**")
		.excludePathPatterns("/images/**");
		
			
	}
	
	
	
	

}
