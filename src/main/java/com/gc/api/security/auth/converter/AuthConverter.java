package com.gc.api.security.auth.converter;

import com.gc.api.common.enums.SocialProvider;
import com.gc.api.security.auth.dto.AuthResponseDTO;

public class AuthConverter {

	public static AuthResponseDTO.LoginResultDTO toLoginResultDTO(
		String refreshToken, Long userId, SocialProvider socialProvider, String email, String nickname,
		String profileImage) {
		return AuthResponseDTO.LoginResultDTO.builder()
			.refreshToken(refreshToken)
			.userId(userId)
			.socialProvider(socialProvider)
			.email(email)
			.nickname(nickname)
			.profileImage(profileImage)
			.build();
	}
}
