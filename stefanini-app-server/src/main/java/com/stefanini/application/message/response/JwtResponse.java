package com.stefanini.application.message.response;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String username;
	private List<String> authorities;

	public JwtResponse(String accessToken, String username, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.username = username;
		this.authorities = authorities
				.stream()
				.map(it -> it.getAuthority().replaceFirst("ROLE_", "").toLowerCase())
				.collect(Collectors.toList());
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getAuthorities() {
		return authorities;
	}
}
