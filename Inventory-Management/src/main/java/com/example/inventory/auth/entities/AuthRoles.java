package com.example.inventory.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auth_roles_tbl")
public class AuthRoles {
	
	@Id
	@GeneratedValue
	@Column(name = "role_id")
	private Integer id;
	
	@Column(name = "role_name")
	private String roleName;

	public AuthRoles(Integer id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	
	public AuthRoles() {}

	@Override
	public String toString() {
		return "AuthRoles [id=" + id + ", roleName=" + roleName + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
