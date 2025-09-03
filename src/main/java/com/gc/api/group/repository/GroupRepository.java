package com.gc.api.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gc.api.group.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long>, GroupRepositoryCustom {
}
