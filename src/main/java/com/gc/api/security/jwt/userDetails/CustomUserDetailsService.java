package com.gc.api.security.jwt.userDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gc.api.member.domain.Member;
import com.gc.api.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findMemberByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

		return CustomUserDetails.from(member);
	}
}
