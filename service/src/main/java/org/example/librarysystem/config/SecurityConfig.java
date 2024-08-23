package org.example.librarysystem.config;

import org.example.librarysystem.filter.JwtAuthenticationTokenFilter;
import org.example.librarysystem.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtTokenUtils jwtTokenUtils;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	@Autowired
	private LoginFailureHandler loginFailureHandler;
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private JWTLogoutSuccessHandler logoutSuccessHandler;
//	@Autowired
//	private UserDetailServiceImpl userDetailService;
	public SecurityConfig(JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenUtils jwtTokenUtils) {

		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtTokenUtils = jwtTokenUtils;

	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
//		for (String path : securityProperties.getPermitAllPaths()) {
//			http.authorizeRequests().antMatchers(path).permitAll();
//		}
		http
			.csrf().disable()
			//登录配置
			.formLogin()
			.successHandler(loginSuccessHandler)
			.failureHandler(loginFailureHandler)
//			.and()
//			.logout()
//			.logoutUrl("/laboratory/auth/logout")
//			.logoutSuccessHandler(logoutSuccessHandler)
			//异常处理
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			.and()
			.headers()
			.frameOptions()
			.disable()
			//不创建会话
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			// 配置拦截规则
			.and()
			.authorizeRequests()
			.antMatchers(
				HttpMethod.GET,
				"/*.html",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js",
				"/webSocket/**"
			).permitAll()
			// 放行swagger
			.antMatchers("/swagger-ui.html").permitAll()
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/*/api-docs/**").permitAll()

			// 放行文件访问
			.antMatchers("/file/**").permitAll()

			// 放行druid
			.antMatchers("/druid/**").permitAll()

			// 放行OPTIONS请求
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			//注册接口
			.antMatchers( "/laboratory/auth/register").permitAll()
			//获取公钥
			.antMatchers( "/laboratory/auth/publicKey").permitAll()
			//没有登录修改密码
			.antMatchers( "/laboratory/auth/updateUserPwd").permitAll()
			//允许匿名及登录用户访问
			.antMatchers("/laboratory/auth/login", "/error/**").permitAll()
			// 所有请求都需要认证
			.anyRequest().authenticated()
			//添加JWT filter
			.and()
			.addFilterBefore(new JwtAuthenticationTokenFilter(jwtTokenUtils),UsernamePasswordAuthenticationFilter.class);

		// 禁用缓存
		http.headers().cacheControl();

		// 添加JWT filter
//		http
//			.apply(new TokenConfigurer(jwtTokenUtils));
	}
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(sysUserService.loadUserByUsername(auth.userDetailsService().));
//	}

	public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

		private final JwtTokenUtils jwtTokenUtils;

		public TokenConfigurer(JwtTokenUtils jwtTokenUtils){

			this.jwtTokenUtils = jwtTokenUtils;
		}

		@Override
		public void configure(HttpSecurity http) {
			JwtAuthenticationTokenFilter customFilter = new JwtAuthenticationTokenFilter(jwtTokenUtils);
			http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
		}
	}
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return (PasswordEncoder) new BCryptPasswordEncoder();
//	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


}
