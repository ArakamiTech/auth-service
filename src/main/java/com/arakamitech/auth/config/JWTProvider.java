package com.arakamitech.auth.config;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.arakamitech.auth.entities.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTProvider {
	
	@Value("${jwt.secret}")
	private String secret;

	public String createToken(UserEntity userEntity) {
		Map<String, Object> claims = Jwts.claims().setSubject(userEntity.getNombreUsuario());
		claims.put("id", userEntity.getId());
		Date now = new Date();
		Date exp = new Date(now.getTime() + 3600 * 1000);
		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public void validate(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	public String getUsernameFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
		}
	}

}
