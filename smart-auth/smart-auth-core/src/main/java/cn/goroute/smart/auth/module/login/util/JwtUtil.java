package cn.goroute.smart.auth.module.login.util;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Alickx
 * @Date: 2023/02/21 12:25:58
 * @Description:
 */
@Component
@Slf4j
public class JwtUtil {

	/**
	 * 密钥
	 */
	@Value("${jwt.secretKey}")
	private String secretKey;

	private final Integer ExpireTimeSec = 60 * 60 * 24;


	private String getBase64SecurityKey() {
		return Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/**
	 * 生成 Token
	 */
	public String createUserActivateToken(String username, Map<String, Object> claims) {
		byte[] keyBytes = Decoders.BASE64.decode(getBase64SecurityKey());
		Key key = Keys.hmacShaKeyFor(keyBytes);

		Date now = new Date();

		return Jwts.builder()
				.setClaims(claims)
				// 面向的用户
				.setSubject(username)
				// 签发时间
				.setIssuedAt(now)
				// 过期时间
				.setExpiration(DateUtils.addSeconds(now,ExpireTimeSec))
				// 签名算法、签名密钥
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * 从 Token 中获取 username
	 */
	public String getUsernameFromToken(String token) {
		try {
			final Claims claims = getClaimsFromToken(token);
			if (Objects.isNull(claims) || StrUtil.isEmpty(claims.getSubject())) {
				return null;
			}
			return claims.getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据键值从 Token 中获取属性值
	 */
	public String get(String token, String key) {
		Claims claims = getClaimsFromToken(token);
		if (Objects.isNull(claims) || StrUtil.isBlank(key)) {
			return null;
		}
		Object value = claims.get(key);
		return Objects.isNull(value) ? null : String.valueOf(value);
	}

	/**
	 * 获取载荷
	 *
	 * @param token token
	 * @return
	 */
	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			byte[] keyBytes = Decoders.BASE64.decode(getBase64SecurityKey());
			Key key = Keys.hmacShaKeyFor(keyBytes);
			claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			return null;
		}
		return claims;
	}

	/**
	 * 验证 Token 是否有效或过期
	 *
	 * @param token token
	 * @return boolean 是否有效 true 有效 false 无效
	 */
	public boolean validateToken(String token) {
		try {
			final Claims claims = getClaimsFromToken(token);
			return !Objects.isNull(claims) && !StrUtil.isEmpty(claims.getSubject());
		} catch (Exception e) {
			return false;
		}
	}
}
