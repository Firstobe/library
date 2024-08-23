package org.example.librarysystem.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	JwtTokenUtils jwtTokenUtils;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		Map map = new HashMap();
		map.put("user",authentication.getName());
		map.put("password",authentication.getCredentials());
		// 生成JWT，并放置到请求头中
		String jwt = jwtTokenUtils.createToken(map);
		response.setHeader("Authorization", jwt);
		Result result = Result.succ("SuccessLogin");
		outputStream.write(JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8));
		outputStream.flush();
		outputStream.close();

	}


	/*
	 * @Author cms
	 * @Description //TODO $
	 * @Date $ $
	 * @Param $
	 * @return $
	 **/
}
