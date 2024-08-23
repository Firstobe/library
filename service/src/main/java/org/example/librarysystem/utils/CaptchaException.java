package org.example.librarysystem.utils;

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException {
	/*
	 * @Author cms
	 * @Description //TODO $
	 * @Date $ $
	 * @Param $
	 * @return $
	 **/
	public CaptchaException(String msg) {
		super(msg);
	}

}
