package com.gc.api.security.auth.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gc.api.common.enums.SocialProvider;
import com.gc.api.member.domain.Member;
import com.gc.api.member.repository.MemberRepository;
import com.gc.api.payload.exception.GeneralException;
import com.gc.api.payload.status.MemberErrorStatus;
import com.gc.api.security.jwt.enums.RedisTokenType;
import com.gc.api.security.jwt.provider.JwtProvider;
import com.gc.api.security.jwt.redis.TokenManager;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final TokenManager tokenManager;

	@Override
	public Member logout(HttpServletRequest request) {
		// 요청에서 이메일 및 소셜 제공자 추출
		String accessToken = jwtProvider.resolveToken(request);
		String loginId = jwtProvider.getSubject(accessToken);
		SocialProvider socialProvider = jwtProvider.getSocialProvider(accessToken);
		// 액세스 토큰 블랙리스트화 및 리프레시 토큰 삭제
		tokenManager
			.saveToken(RedisTokenType.LOGOUT_ACCESS_TOKEN, loginId, accessToken);
		if (tokenManager.findToken(RedisTokenType.REFRESH_TOKEN, loginId, accessToken)) {
			tokenManager.removeToken(RedisTokenType.REFRESH_TOKEN, loginId, accessToken);
		}
		// 사용자 반환
		return memberRepository.findMemberByEmailAndSocialProvider(loginId, socialProvider)
			.orElseThrow(() -> new GeneralException(MemberErrorStatus.EMAIL_NOT_FOUND));
	}

	@Override
	public void inactivateMember(HttpServletRequest request) {
		// 로그아웃 후 사용자 비활성화
		Member member = logout(request);
		member.inactivate();
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void deleteInactiveMember() {
		// 30일 이상 비활성화된 사용자 삭제
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(30);
		log.info("비활성 사용자 삭제 작업 시작(기준 시간: {})", localDateTime);
		int deletedMemberCount = memberRepository.deleteMembersBefore(localDateTime);
		if (deletedMemberCount > 0) {
			log.info("총 {}명의 비활성 사용자 삭제", deletedMemberCount);
		} else {
			log.info("비활성 사용자 없음");
		}
	}
}
