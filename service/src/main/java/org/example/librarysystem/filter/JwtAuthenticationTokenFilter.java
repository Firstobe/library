package org.example.librarysystem.filter;

import lombok.extern.slf4j.Slf4j;
import org.example.librarysystem.config.JwtSecurityProperties;
import org.example.librarysystem.utils.JwtTokenUtils;
import org.example.librarysystem.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt校验过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	@Autowired
	private UserDetailsService userDetailsService;
	public JwtAuthenticationTokenFilter(JwtTokenUtils jwtTokenUtils) {
		this.jwtTokenUtils = jwtTokenUtils;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		JwtSecurityProperties jwtSecurityProperties = SpringContextHolder.getBean(JwtSecurityProperties.class);
		String requestRri = request.getRequestURI();
		//获取request token
		String token = null;
		String bearerToken = request.getHeader(jwtSecurityProperties.getHeader());
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtSecurityProperties.getTokenStartWith())) {
			token = bearerToken.substring(jwtSecurityProperties.getTokenStartWith().length());
		}
//		String  redisToken = stringRedisTemplate.opsForValue().get("token:"+token);
//		if (!StringUtils.hasLength(redisToken)){
//			throw new RuntimeException("token失效");
//		}
		if (StringUtils.hasText(token) && jwtTokenUtils.validateToken(token)) {
			Authentication authentication = jwtTokenUtils.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestRri);
		} else {
			log.debug("no valid JWT token found, uri: {}", requestRri);
		}
		filterChain.doFilter(request, response);


}
	/*
	 * @Author cms
	 * @Description //TODO $
	 * @Date $ $
	 * @Param $
	 * @return $
	 **/

}
