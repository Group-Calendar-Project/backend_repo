package com.gc.api.common.uuid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gc.api.common.uuid.domain.Uuid;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
	Optional<Uuid> findByUuid(String uuid);
}
