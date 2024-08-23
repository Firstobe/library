package org.example.librarysystem.utils;

import com.alibaba.fastjson.JSONObject;
import org.example.librarysystem.config.JwtSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JWTLogoutSuccessHandler implements LogoutSuccessHandler {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	@Override
	public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException, IOException {
		String token = null;
		JwtSecurityProperties jwtSecurityProperties = SpringContextHolder.getBean(JwtSecurityProperties.class);
		String bearerToken = httpServletRequest.getHeader(jwtSecurityProperties.getHeader());
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtSecurityProperties.getTokenStartWith())) {
			token = bearerToken.substring(jwtSecurityProperties.getTokenStartWith().length());
		}

		if (StringUtils.hasText(token) && jwtTokenUtils.validateToken(token)) {
			 authentication = jwtTokenUtils.getAuthentication(token);
		}
		if (authentication != null) {
			stringRedisTemplate.delete("token:" + token);
			new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
		}

		httpServletResponse.setContentType("application/json;charset=UTF-8");
		ServletOutputStream outputStream = httpServletResponse.getOutputStream();

		httpServletResponse.setHeader("Authorization", "");

		Result result = Result.succ("SuccessLogout");

		outputStream.write(JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8));
		outputStream.flush();
		outputStream.close();
	}
}

