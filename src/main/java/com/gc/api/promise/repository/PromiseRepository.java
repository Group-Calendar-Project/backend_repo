package com.gc.api.promise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gc.api.promise.domain.Promise;

public interface PromiseRepository extends JpaRepository<Promise, Long>, PromiseRepositoryCustom {
}
