package com.gc.api.member.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gc.api.common.enums.SocialProvider;
import com.gc.api.member.domain.Member;

import io.lettuce.core.dynamic.annotation.Param;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

	Optional<Member> findMemberByEmail(String email);

	Optional<Member> findMemberByEmailAndSocialProvider(String email, SocialProvider socialProvider);

	@Modifying
	@Query("delete from Member m where m.inactiveDate < :targetDate")
	int deleteMembersBefore(@Param("targetDate") LocalDateTime targetDate);
}
