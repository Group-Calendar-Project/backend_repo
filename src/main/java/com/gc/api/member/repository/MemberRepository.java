package com.gc.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gc.api.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}
