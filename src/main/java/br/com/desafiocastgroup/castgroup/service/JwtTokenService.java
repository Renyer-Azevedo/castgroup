package br.com.desafiocastgroup.castgroup.service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	
	@Value("${jwt.expire}")
	private long expire;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public JwtTokenService() {
		super();
	}
	
	public String extractLogin(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String login) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, login);
	}

	private String createToken(Map<String, Object> claims, String login) {
		return Jwts.builder().setClaims(claims).setSubject(login).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.expire))
				.signWith(SignatureAlgorithm.HS256, this.secretKey).compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetail) {
		final String username = extractLogin(token);
		return (username.equals(userDetail.getUsername()) && !isTokenExpired(token));
	}
}

