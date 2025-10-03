package com.gc.api.security.auth.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gc.api.common.enums.SocialProvider;
import com.gc.api.member.domain.Member;
import com.gc.api.member.repository.MemberRepository;
import com.gc.api.payload.exception.GeneralException;
import com.gc.api.payload.status.JwtErrorStatus;
import com.gc.api.payload.status.MemberErrorStatus;
import com.gc.api.security.auth.converter.AuthConverter;
import com.gc.api.security.auth.dto.AuthResponseDTO;
import com.gc.api.security.jwt.enums.RedisTokenType;
import com.gc.api.security.jwt.provider.CustomAuthenticationProvider;
import com.gc.api.security.jwt.provider.JwtProvider;
import com.gc.api.security.jwt.redis.TokenManager;
import com.gc.api.security.jwt.token.CustomAuthenticationToken;
import com.gc.api.security.jwt.userDetails.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	private final CustomAuthenticationProvider customAuthenticationProvider;

	public AuthResponseDTO.LoginResultDTO createLoginResult(
		String loginId, Authentication request, HttpServletResponse response) {
		// 토큰에서 정보 추출
		CustomUserDetails customUserDetails = (CustomUserDetails)request.getPrincipal();
		Long memberId = customUserDetails.getId();
		String nickname = customUserDetails.getNickname();
		String email = customUserDetails.getEmail();
		SocialProvider socialProvider = customUserDetails.getSocialProvider();
		String profileImage = customUserDetails.getProfileImage();
		// 새로운 토큰 발급
		String accessToken = jwtProvider.createAccessToken(request);
		String refreshToken = jwtProvider.createRefreshToken(request);
		// 리프레시 토큰 저장
		tokenManager.saveToken(RedisTokenType.REFRESH_TOKEN, loginId, refreshToken);
		// 응답 작성
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Authorization", "Bearer " + accessToken);
		// 인증 설정
		SecurityContextHolder.getContext().setAuthentication(request);
		// DTO 반환
		return AuthConverter.toLoginResultDTO(refreshToken, memberId, socialProvider, email, nickname, profileImage);
	}

	@Override
	public AuthResponseDTO.LoginResultDTO reissue(String refreshToken, HttpServletResponse response) {
		// 리프레시 토큰 검증
		if (refreshToken == null || refreshToken.isEmpty()) {
			throw new GeneralException(JwtErrorStatus.REFRESH_TOKEN_NOT_FOUND);
		}
		// 리프레시 토큰에서 이메일과 소셜 제공자 추출
		String loginId = jwtProvider.getSubject(refreshToken);
		SocialProvider socialProvider = jwtProvider.getSocialProvider(refreshToken);
		// 인증 및 토큰 생성
		Authentication requestToken = new CustomAuthenticationToken(refreshToken, socialProvider);
		try {
			Authentication responseToken = customAuthenticationProvider.authenticate(requestToken);
			return createLoginResult(loginId, responseToken, response);
		} catch (UsernameNotFoundException e) {
			log.error("사용자 확인 불가: {}", e.getMessage());
			throw new GeneralException(MemberErrorStatus.EMAIL_NOT_FOUND);
		} catch (Exception e) {
			log.error("토큰 재발급 중 에러 발생: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.LOGIN_UNKNOWN_ERROR);
		}
	}

	@Override
	public Member logout(HttpServletRequest request) {
		// 요청에서 토큰 추출 후 이메일과 소셜 제공자 추출
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
