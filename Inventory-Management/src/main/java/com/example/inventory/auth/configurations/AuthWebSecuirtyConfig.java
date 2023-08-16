package com.example.inventory.auth.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.inventory.auth.services.AuthUserService;

@Configuration
@EnableWebSecurity
public class AuthWebSecuirtyConfig {
	
	Logger logger = LoggerFactory.getLogger(AuthWebSecuirtyConfig.class);
	
	@Autowired
	private AuthEntryPoint entryPoint;
	
	@Autowired
	private AuthAccessDeniedHandler deniedHandler;
	
	@Autowired
	private AuthUserService userService;
	
	@Bean
	public AuthFilter authFilter() {
		logger.info("Fetching pre authentication filter before request dispatching");
		return new AuthFilter();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		logger.info("Fetching password encryptor");
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		logger.info("Fetching authentication manager object");
	    return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		  logger.info("Fetching custom authentication provider details");
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	      authProvider.setUserDetailsService(userService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	}
	
	/*
  Security filter chain: [
  WebAsyncManagerIntegrationFilter
  SecurityContextPersistenceFilter
  HeaderWriterFilter
  CorsFilter
  LogoutFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  SessionManagementFilter
  ExceptionTranslationFilter
  FilterSecurityInterceptor
] 
	 */
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		 logger.info("Entering security layer, Filtering requests end points");
		 http
		 .csrf(csrf -> csrf.disable())
		 .exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint))
		 .exceptionHandling(exception -> exception.accessDeniedHandler(deniedHandler))
		 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 .authorizeHttpRequests(
				  auth -> auth.requestMatchers(HttpMethod.POST, "/auth/api/register", "/auth/api/login", "/auth/api/grant-admin").permitAll()
				              .requestMatchers(HttpMethod.POST, "/shop/api/create-new-product", "/shop/api/create-new-products").hasAuthority("VENDOR")
				              .requestMatchers(HttpMethod.GET,  "/shop/api/get-products-by-company/**", "/shop/api/get-all-products", "/get-products-of-category", "/get-products-of-category-and-price").hasAnyAuthority("USER","VENDOR")
				              .anyRequest().authenticated());
		 
		 http.authenticationProvider(authenticationProvider());
		 http.addFilterAfter(authFilter(), ExceptionTranslationFilter.class);
		 logger.info("Exiting security layer.......");
		 return http.build();
	 }
}
