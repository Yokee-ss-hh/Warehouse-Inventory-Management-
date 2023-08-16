package com.example.inventory.auth.entities;


import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "auth_user_tbl")
public class AuthUser {
	
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Integer id;
	
	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_password")
	private String password;
	
	@Column(name = "user_email")
	private String email;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "auth_token_fk")
	private AuthToken token;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "auth_users_roles",
	           joinColumns = @JoinColumn(name = "user_id"),
	           inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<AuthRoles> roles;


	public AuthUser(Integer id, String name, String password, String email, AuthToken token, Set<AuthRoles> roles) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.token = token;
		this.roles = roles;
	}
	
	public AuthUser() {}

	@Override
	public String toString() {
		return "AuthUser [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", token="
				+ token + ", roles=" + roles + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthToken getToken() {
		return token;
	}

	public void setToken(AuthToken token) {
		this.token = token;
	}

	public Set<AuthRoles> getRoles() {
		return roles;
	}

	public void setRoles(Set<AuthRoles> roles) {
		this.roles = roles;
	}
}
