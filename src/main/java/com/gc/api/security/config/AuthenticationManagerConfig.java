package com.gc.api.security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import com.gc.api.security.jwt.provider.CustomAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

	private final CustomAuthenticationProvider customAuthenticationProvider;

	@Bean
	public AuthenticationManager authenticationManager() {
		List<AuthenticationProvider> providers = List.of(
			customAuthenticationProvider
		);
		return new ProviderManager(providers);
	}
}
