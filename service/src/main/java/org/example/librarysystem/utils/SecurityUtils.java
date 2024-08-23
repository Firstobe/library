package org.example.librarysystem.utils;

import org.example.librarysystem.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	private SecurityUtils() {
	}

	/**
	 * 获得当前用户的编号，从上下文
	 *
	 * @return 用户编号
	 */
	public static String getLoginUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUser) {
			LoginUser user = (LoginUser) authentication.getPrincipal();
			return user.getUserInfo().getId();
		}
		return null;
	}

}
