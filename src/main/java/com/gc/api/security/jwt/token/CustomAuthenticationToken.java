package com.gc.api.security.jwt.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gc.api.common.enums.SocialProvider;

import lombok.Getter;

@Getter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

	private final SocialProvider socialProvider;

	public CustomAuthenticationToken(Object principal, SocialProvider socialProvider) {
		super(null);
		super.setDetails(principal);
		this.socialProvider = socialProvider;
		setAuthenticated(false);
	}

	public CustomAuthenticationToken(UserDetails userDetails, SocialProvider socialProvider,
		Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setDetails(userDetails);
		this.socialProvider = socialProvider;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return super.getDetails();
	}
}
