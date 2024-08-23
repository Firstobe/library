package org.example.librarysystem.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;

@Configuration
public class WebAutoConfiguration {

	// ========== Filter 相关 ==========

	/**
	 * 创建 CorsFilter Bean，解决跨域问题
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilterBean() {
		// 创建 CorsConfiguration 对象
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*"); // 设置访问源地址
		config.addAllowedHeader("*"); // 设置访问源请求头
		config.addAllowedMethod("*"); // 设置访问源请求方法
		// 创建 UrlBasedCorsConfigurationSource 对象
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config); // 对接口配置跨域设置
		return createFilterBean(new CorsFilter(source), Integer.MAX_VALUE);
	}

	public static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
		FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
		bean.setOrder(order);
		return bean;
	}

}
