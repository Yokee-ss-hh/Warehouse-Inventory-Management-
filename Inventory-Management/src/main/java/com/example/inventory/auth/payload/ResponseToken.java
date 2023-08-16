package com.example.inventory.auth.payload;

import java.time.LocalDateTime;

public class ResponseToken {
	
	private String accessToken;
	
	private String accessTokenType;
	
	private String refreshToken;
	
	private String refreshTokenType;
	
	private LocalDateTime accessTokenCreated;
	
	private LocalDateTime accessTokenExpiration;
	
	private LocalDateTime refreshTokenCreated;
	
	private LocalDateTime refreshTokenExpiration;

	public ResponseToken(String accessToken, String accessTokenType, String refreshToken, String refreshTokenType,
			LocalDateTime accessTokenCreated, LocalDateTime accessTokenExpiration, LocalDateTime refreshTokenCreated,
			LocalDateTime refreshTokenExpiration) {
		super();
		this.accessToken = accessToken;
		this.accessTokenType = accessTokenType;
		this.refreshToken = refreshToken;
		this.refreshTokenType = refreshTokenType;
		this.accessTokenCreated = accessTokenCreated;
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenCreated = refreshTokenCreated;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}
	
	public ResponseToken() {}

	@Override
	public String toString() {
		return "ResponseToken [accessToken=" + accessToken + ", accessTokenType=" + accessTokenType + ", refreshToken="
				+ refreshToken + ", refreshTokenType=" + refreshTokenType + ", accessTokenCreated=" + accessTokenCreated
				+ ", accessTokenExpiration=" + accessTokenExpiration + ", refreshTokenCreated=" + refreshTokenCreated
				+ ", refreshTokenExpiration=" + refreshTokenExpiration + "]";
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenType() {
		return accessTokenType;
	}

	public void setAccessTokenType(String accessTokenType) {
		this.accessTokenType = accessTokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRefreshTokenType() {
		return refreshTokenType;
	}

	public void setRefreshTokenType(String refreshTokenType) {
		this.refreshTokenType = refreshTokenType;
	}

	public LocalDateTime getAccessTokenCreated() {
		return accessTokenCreated;
	}

	public void setAccessTokenCreated(LocalDateTime accessTokenCreated) {
		this.accessTokenCreated = accessTokenCreated;
	}

	public LocalDateTime getAccessTokenExpiration() {
		return accessTokenExpiration;
	}

	public void setAccessTokenExpiration(LocalDateTime accessTokenExpiration) {
		this.accessTokenExpiration = accessTokenExpiration;
	}

	public LocalDateTime getRefreshTokenCreated() {
		return refreshTokenCreated;
	}

	public void setRefreshTokenCreated(LocalDateTime refreshTokenCreated) {
		this.refreshTokenCreated = refreshTokenCreated;
	}

	public LocalDateTime getRefreshTokenExpiration() {
		return refreshTokenExpiration;
	}

	public void setRefreshTokenExpiration(LocalDateTime refreshTokenExpiration) {
		this.refreshTokenExpiration = refreshTokenExpiration;
	}
}
