package com.gc.api.label.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gc.api.label.domain.Label;

public interface LabelRepository extends JpaRepository<Label, Long>, LabelRepositoryCustom {
}
