package com.example.inventory.auth.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auth_token_tbl")
public class AuthToken {
	
	@Id
	@GeneratedValue
	@Column(name = "auth_token_id")
	private Integer id;
	
	@Column(name = "access_token")
	private String accessToken;
	
	@Column(name = "refresh_token")
	private String refreshToken;
	
	@Column(name = "access_token_created")
	private LocalDateTime accessTokenCreation;
	
	@Column(name = "access_token_expiration")
	private LocalDateTime accessTokenExpiration;
	
	@Column(name = "refresh_token_created")
	private LocalDateTime refreshTokenCreation;
	
	@Column(name = "refresh_token_expiration")
	private LocalDateTime refreshTokenExpiration;

	public AuthToken(Integer id, String accessToken, String refreshToken, LocalDateTime accessTokenCreation,
			LocalDateTime accessTokenExpiration, LocalDateTime refreshTokenCreation,
			LocalDateTime refreshTokenExpiration) {
		super();
		this.id = id;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.accessTokenCreation = accessTokenCreation;
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenCreation = refreshTokenCreation;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}
	
	public AuthToken() {}

	@Override
	public String toString() {
		return "AuthToken [id=" + id + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken
				+ ", accessTokenCreation=" + accessTokenCreation + ", accessTokenExpiration=" + accessTokenExpiration
				+ ", refreshTokenCreation=" + refreshTokenCreation + ", refreshTokenExpiration="
				+ refreshTokenExpiration + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public LocalDateTime getAccessTokenCreation() {
		return accessTokenCreation;
	}

	public void setAccessTokenCreation(LocalDateTime accessTokenCreation) {
		this.accessTokenCreation = accessTokenCreation;
	}

	public LocalDateTime getAccessTokenExpiration() {
		return accessTokenExpiration;
	}

	public void setAccessTokenExpiration(LocalDateTime accessTokenExpiration) {
		this.accessTokenExpiration = accessTokenExpiration;
	}

	public LocalDateTime getRefreshTokenCreation() {
		return refreshTokenCreation;
	}

	public void setRefreshTokenCreation(LocalDateTime refreshTokenCreation) {
		this.refreshTokenCreation = refreshTokenCreation;
	}

	public LocalDateTime getRefreshTokenExpiration() {
		return refreshTokenExpiration;
	}

	public void setRefreshTokenExpiration(LocalDateTime refreshTokenExpiration) {
		this.refreshTokenExpiration = refreshTokenExpiration;
	}
}
