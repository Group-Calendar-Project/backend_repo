package com.gc.api.security.jwt.userDetails;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gc.api.common.enums.SocialProvider;
import com.gc.api.member.domain.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final Long id;
	private final String email;
	private final String nickname;
	private final String password;
	private final SocialProvider socialProvider;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean enabled;

	public static CustomUserDetails from(Member member) {
		Collection<GrantedAuthority> authorities = Collections.singletonList(
			new SimpleGrantedAuthority(member.getMemberRole().name())
		);

		return new CustomUserDetails(
			member.getId(),
			member.getEmail(),
			member.getNickname(),
			null,
			member.getSocialProvider(),
			authorities,
			true
		);
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
		return this.enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
}
