package com.example.inventory.auth.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.inventory.auth.entities.AuthUser;
import com.example.inventory.auth.repositories.AuthUserRepository;

@Service
public class AuthUserService implements UserDetailsService{
	
	@Autowired
	private AuthUserRepository authUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthUser authUser = authUserRepository
				.findByName(username)
				.orElseThrow(
						() -> new UsernameNotFoundException(String.format("The user with username as %s is not found",username))
						);
	    return AuthUserDetailsService.build(authUser);
	}	
}
