package com.example.inventory.auth.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.auth.configurations.AuthUtils;
import com.example.inventory.auth.entities.AuthRoles;
import com.example.inventory.auth.entities.AuthToken;
import com.example.inventory.auth.entities.AuthUser;
import com.example.inventory.auth.payload.RequestLoginUser;
import com.example.inventory.auth.payload.RequestRegisterUser;
import com.example.inventory.auth.payload.ResponseToken;
import com.example.inventory.auth.repositories.AuthUserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/auth/api")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthUserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthUtils utils;
	
	@Autowired
	private Environment environment;
	
	@PostMapping(path = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String registerUser(@RequestBody RequestRegisterUser user) {
		if(!userRepo.existsByName(user.getUserName()) && !userRepo.existsByEmail(user.getEmail())) {
			AuthUser authUser = new AuthUser();
			authUser.setName(user.getUserName());
			authUser.setPassword(encoder.encode(user.getPassWord()));
			authUser.setEmail(user.getEmail());
			
			AuthRoles authRoles = new AuthRoles();
			authRoles.setRoleName("USER");
			
			Set<AuthRoles> defaultRoles = new HashSet<AuthRoles>();
			defaultRoles.add(authRoles);
			
			authUser.setRoles(defaultRoles);
			
			userRepo.save(authUser);
		    return "User is created successfully!!";
		}
		else {
			return "User (or) Email already exists, Please try again with new credentials";
		}
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<ResponseToken> loginUser(@RequestBody RequestLoginUser user){
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassWord());
		
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    String jwtAccessToken = utils.generateAccessToken(authentication);
	    String jwtRefreshToken = utils.generateRefreshToken(authentication);
	    
	    AuthUser authUser = userRepo.findByName(user.getUserName()).get();
	    if(authUser != null) {
	    	AuthToken token = new AuthToken();
	    	token.setAccessToken(jwtAccessToken);
	    	token.setAccessTokenCreation(LocalDateTime.now());
	    	token.setAccessTokenExpiration(LocalDateTime
	    			.ofInstant(utils
	    					.extractExpiration(jwtAccessToken).toInstant(), ZoneId.systemDefault()));
	    	token.setRefreshToken(jwtRefreshToken);
	    	token.setRefreshTokenCreation(LocalDateTime.now());
	    	token.setRefreshTokenExpiration(LocalDateTime
	    			.ofInstant(utils
	    					.extractExpiration(jwtRefreshToken).toInstant(), ZoneId.systemDefault()));
	    	authUser.setToken(token);
	    	userRepo.save(authUser);
	    	ResponseToken responseToken = new ResponseToken(
	    			jwtAccessToken,
	    			"JWT",
	    			jwtRefreshToken,
	    			"JWT",
	    			LocalDateTime.now(),
	    			LocalDateTime
	    			.ofInstant(utils
	    					.extractExpiration(jwtAccessToken).toInstant(), ZoneId.systemDefault()),
	    			LocalDateTime.now(),
	    			LocalDateTime
	    			.ofInstant(utils
	    					.extractExpiration(jwtRefreshToken).toInstant(), ZoneId.systemDefault())
	    			);
	    	return ResponseEntity.status(HttpStatus.OK).body(responseToken);
	    }
	    else {
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseToken());
	    }
	}
	
	@PostMapping(path = "/grant-admin")
	public ResponseEntity<String> grantAdmin(HttpServletRequest request, @RequestBody RequestLoginUser user){
		String secretKeyFromClient = request.getHeader("securityAdminGrantToken");
		if(secretKeyFromClient != null && user != null) {
			AuthUser authUser = userRepo.findByName(user.getUserName()).get();
			String secretKeyFromServer = environment.getProperty("securityAdminGrantToken").toString();
			if(secretKeyFromClient.equals(secretKeyFromServer) && authUser != null) {
				Set<AuthRoles> currentRoles = authUser.getRoles();
				AuthRoles authRoles = new AuthRoles();
				authRoles.setRoleName("VENDOR");
				currentRoles.add(authRoles);
				userRepo.save(authUser);
				return ResponseEntity.status(HttpStatus.CREATED).body("VENDOR permission granted!!");
			}
			else {
				 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Secret key is wrong/ user details not found!!");
			}
		}
		else {
		      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Secret key/ user name / password is not passed");
		}
	}
}
