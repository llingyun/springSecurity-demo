package bnu.edu.cn.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import bnu.edu.cn.filter.TimeFilter;
import bnu.edu.cn.intercepter.TimeInterceptor;
import groovy.lang.Interceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

	@Autowired
	TimeInterceptor intercetor;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("login").setViewName("login");
	}
	
	@Bean
	public FilterRegistrationBean timeFilter() {
		FilterRegistrationBean filter = new FilterRegistrationBean();
		TimeFilter timeFiter = new TimeFilter();
		filter.setFilter(timeFiter);
		List<String> urls = new ArrayList<>();
		urls.add("/*");
		filter.setUrlPatterns(urls);
		return filter;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(intercetor);
	}
	
}
