package com.example.inventory.auth.configurations;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.inventory.auth.entities.AuthToken;
import com.example.inventory.auth.entities.AuthUser;
import com.example.inventory.auth.repositories.AuthTokenRepository;
import com.example.inventory.auth.repositories.AuthUserRepository;
import com.example.inventory.auth.services.AuthUserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthFilter extends OncePerRequestFilter{
	
	Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	
	private static final String[] excludedEndpoints = new String[] {"/auth/api/register", "/auth/api/login", "/auth/api/grant-admin"};
	
	@Autowired
	private AuthUtils authUtils;
	
	@Autowired
	private AuthUserService service;
	
	@Autowired
	private AuthUserRepository userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, java.io.IOException {
	
		try {
			logger.info("Entering authentication filter!!!!");
			String authToken = parseJwt(request);
			if(authToken != null && authUtils.validateJwtToken(authToken) && isTokenPresentInDb(authToken)) {
				logger.info("Auth token is validated, Fetching details from DB");
				String username = authUtils.getUserNameFromJwtToken(authToken);
				UserDetails userDetails = service.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authenticationObject = 
						new UsernamePasswordAuthenticationToken(
								userDetails.getUsername(), 
								userDetails.getPassword(),
								userDetails.getAuthorities());
				authenticationObject.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		        SecurityContextHolder.getContext().setAuthentication(authenticationObject);
		        logger.info("Authentication is successful");
			}
			else {
				throw new Exception("Token is Null (or) token expired (or) token is not present in DB");
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		filterChain.doFilter(request, response);
		logger.info("Filtering is completed, Bypassing request to further process");
	}
	
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    	logger.info("Omitting request from filtering");
    	return Arrays.stream(excludedEndpoints)
    	        .anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
    }
    
	private String parseJwt(HttpServletRequest request) {
		logger.info("Parsing JWT from the request");
	    String headerAuth = request.getHeader("Authorization");

	    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
	      return headerAuth.substring(7);
	    }

	    return null;
	}
	
	private Boolean isTokenPresentInDb(String token) {
		String user = authUtils.getUserNameFromJwtToken(token);
		boolean isUserPresentInDb = userRepo.existsByName(user);
		if(isUserPresentInDb) {
			AuthUser authUser = userRepo.findByName(user).get();
			AuthToken authTokenFK = authUser.getToken();
			if(authTokenFK != null) {
				String authToken = authTokenFK.getAccessToken();
				if(authToken != null && authToken.equals(token)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
    
}
