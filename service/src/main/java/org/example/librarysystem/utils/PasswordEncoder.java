package org.example.librarysystem.utils;

import lombok.NoArgsConstructor;
import org.example.librarysystem.config.RsaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class PasswordEncoder extends BCryptPasswordEncoder {

@Autowired
private RsaProperties rsaProperties;
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// 接收到的前端的密码
		String pwd = rawPassword.toString();
		String salt = "";
		// 进行rsa解密
//		try {
//			pwd = RsaUtils.decryptByPrivateKey(rsaProperties.getPrivateKey(), encodedPassword);
//		} catch (Exception e) {
//			throw new BadCredentialsException(e.getMessage());
//		}
		if (encodedPassword != null && encodedPassword.length() != 0) {
			return BCrypt.checkpw(pwd, encodedPassword);
		} else {
			return false;
		}
	}
}

