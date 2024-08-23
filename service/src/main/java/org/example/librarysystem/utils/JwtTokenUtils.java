package org.example.librarysystem.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.librarysystem.config.JwtSecurityProperties;
import org.example.librarysystem.entity.LoginUser;
import org.example.librarysystem.entity.UserInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenUtils implements InitializingBean {
@Autowired
private StringRedisTemplate stringRedisTemplate;
	private final JwtSecurityProperties jwtSecurityProperties;
	private static final String AUTHORITIES_KEY = "auth";
	private Key key;

	public JwtTokenUtils(JwtSecurityProperties jwtSecurityProperties) {
		this.jwtSecurityProperties = jwtSecurityProperties;
	}

	@Override
	public void afterPropertiesSet() {
		SecureRandom random = new SecureRandom();
		byte[] secretKey = new byte[256 / 8]; // 对于HS256算法，通常256位足够
		random.nextBytes(secretKey);
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecurityProperties.getBase64Secret());
		this.key = Keys.hmacShaKeyFor(secretKey);
	}

	/**
	 * 生成jwt
	 * @param claims
	 * @return
	 */
	public  String createToken (Map<String, Object> claims) {
		return Jwts.builder()
			.claim(AUTHORITIES_KEY, claims)
			.setId(UUID.randomUUID().toString())
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + jwtSecurityProperties.getTokenValidityInSeconds()))
			.compressWith(CompressionCodecs.DEFLATE)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}
//	 * 判断jwt是否过期
	public boolean  getExpirationDateFromToken(String token) {
		boolean result = false;
		try {
			final Claims claims = getClaimsFromToken(token);
			result = claims.getExpiration().before(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 解析jwt
	 * @param token
	 * @return
	 */
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(key)
			.parseClaimsJws(token)
			.getBody();

		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		HashMap<String, Object> map =(HashMap) claims.get("auth");
		String  redisToken = stringRedisTemplate.opsForValue().get("token:"+token);
		if (!StringUtils.hasLength(redisToken)){
			throw new RuntimeException("token失效");
		}
		UserInfo sysUser = new UserInfo();
		sysUser.setUserName(map.get("userAccount").toString());
		sysUser.setPasswordHash(map.get("userPassword").toString());
		sysUser.setId((String) map.get("id"));
		LoginUser loginUser = new LoginUser(sysUser);
		User principal = new User(map.get("userAccount").toString(), map.get("userPassword").toString(), authorities);
		//将解析后的用户信息给到UsernamePasswordAuthenticationToken做认证校验
		return new UsernamePasswordAuthenticationToken(loginUser, token, authorities);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT signature.");
			e.printStackTrace();
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
			e.printStackTrace();
		}
		return false;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public static void main(String[] args) {
		SecureRandom random = new SecureRandom();
		byte[] secretKey = new byte[256 / 8]; // 对于HS256算法，通常256位足够
		random.nextBytes(secretKey);
		System.out.println(secretKey);
	}
}

