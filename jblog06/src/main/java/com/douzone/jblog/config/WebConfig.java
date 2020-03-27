package com.douzone.jblog.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.douzone.jblog.security.AuthUserHandlerMethodArgumentResolver;
import com.douzone.jblog.security.AuthInterceptor;
import com.douzone.jblog.security.LoginInterceptor;
import com.douzone.jblog.security.LogoutInterceptor;

@Configuration
@PropertySource("classpath:com/douzone/jblog/config/config.properties")
public class WebConfig implements WebMvcConfigurer{
	
	
	@Autowired
	private Environment env;
	
	@Bean
	public HandlerMethodArgumentResolver authUserHandlerMethodArgumentResovler() {
		
		return new AuthUserHandlerMethodArgumentResolver();
		
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
	
		
	}
	
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
		
		registry.addInterceptor(loginInterceptor()).addPathPatterns(env.getProperty("security.auth-url")).addPathPatterns(env.getProperty("security.blogauth-url"));
		
		registry.addInterceptor(logoutInterceptor()).addPathPatterns(env.getProperty("security.logout-url")).addPathPatterns(env.getProperty("security.bloglogout-url"));
		
		registry.addInterceptor(authInterceptor()).addPathPatterns("/**")
		.excludePathPatterns(env.getProperty("security.auth-url"))
		.excludePathPatterns("/user/**")
		.excludePathPatterns("/blog/auth")
		.excludePathPatterns("/blog/logout")
		.excludePathPatterns("/assets/**")
		.excludePathPatterns("/images/**");
		
			
	}
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(env.getProperty("fileupload.resourceMapping")).addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation"));
		
	}
	
	
	
	
}