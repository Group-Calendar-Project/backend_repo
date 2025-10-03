package com.gc.api.security.auth.dto;

import com.gc.api.common.enums.SocialProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponseDTO {

	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoginResultDTO {
		private String refreshToken;
		private Long userId;
		private SocialProvider socialProvider;
		private String email;
		private String nickname;
		private String profileImage;
	}
}
