package com.example.inventory.auth.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.inventory.auth.entities.AuthRoles;
import com.example.inventory.auth.entities.AuthUser;


public class AuthUserDetailsService implements UserDetails{
	
	private String userName;
	
	private String passWord;
	
	private static final long serialVersionUID = 1L;
	
	private List<GrantedAuthority> authorities;

	public AuthUserDetailsService(String userName, String passWord, List<GrantedAuthority> authorities) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.authorities = authorities;
	}
	
	public AuthUserDetailsService() {}
	
	public static AuthUserDetailsService build(AuthUser authUser) {
		Set<GrantedAuthority> roles = new HashSet<>();
		Set<AuthRoles> authRoles = authUser.getRoles();
		for(AuthRoles r: authRoles) {
			roles.add(new SimpleGrantedAuthority(r.getRoleName()));
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);	
		
		return new AuthUserDetailsService(authUser.getName(), authUser.getPassword(), grantedAuthorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
	}

	@Override
	public String getPassword() {
		return passWord;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
