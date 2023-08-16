package com.example.inventory.auth.configurations;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.inventory.auth.services.AuthUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthUtils {
	
	@Value("${app.jwt.secret}")
	private String secretKey;
	
	@Value("${app.jwt.accesstoken.expiration}")
	private int jwtAccessTokenExpirationInMs;
	
	@Value("${app.jwt.refreshtoken.expiration}")
	private int jwtRefreshTokenExpirationInMs;
	
	
	public String generateAccessToken(Authentication authentication) {
		
		AuthUserDetailsService authUserDetailService = (AuthUserDetailsService) authentication.getPrincipal();
		
		return Jwts
				.builder()
				.setSubject(authUserDetailService.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtAccessTokenExpirationInMs))
				.signWith(key(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String generateRefreshToken(Authentication authentication) {
		
		AuthUserDetailsService authUserDetailService = (AuthUserDetailsService) authentication.getPrincipal();
		
		return Jwts
				.builder()
				.setSubject(authUserDetailService.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtRefreshTokenExpirationInMs))
				.signWith(key(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	
	
	private Key key() {
		    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
	
	public String getUserNameFromJwtToken(String token) {
		    return Jwts.parserBuilder().setSigningKey(key()).build()
		               .parseClaimsJws(token).getBody().getSubject();
    }
	
	public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	}

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	
	public boolean validateJwtToken(String authToken) {
		    try {
		      return !isTokenExpired(authToken);
		    }catch (MalformedJwtException e) {
		    	System.out.println(e.getMessage());
		    }
	         catch (IllegalArgumentException e) {
	        	 System.out.println(e.getMessage());
		    }
		    return false;	    
	}
}
