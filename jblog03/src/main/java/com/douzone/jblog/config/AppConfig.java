package com.douzone.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.douzone.jblog.service","com.douzone.jblog.repository","co.douzone.jblog.aspect"})
public class AppConfig {

}
