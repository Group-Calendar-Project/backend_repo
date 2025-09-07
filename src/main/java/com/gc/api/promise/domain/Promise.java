package com.gc.api.promise.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gc.api.common.base.BaseEntity;
import com.gc.api.common.enums.PromiseDeadline;
import com.gc.api.promise.domain.mapping.GroupPromise;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "promises")
public class Promise extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	private LocalTime startTime;

	private LocalTime endTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(10) DEFAULT 'OFF'")
	private PromiseDeadline promiseDeadline;

	@Column()
	private LocalDate startDay;

	@Column()
	private LocalDate endDay;

	@OneToMany(mappedBy = "promise", cascade = CascadeType.ALL)
	@Builder.Default
	private List<GroupPromise> groupPromiseList = new ArrayList<>();

	@OneToMany(mappedBy = "promise", cascade = CascadeType.ALL)
	@Builder.Default
	private List<PromiseCandidateDate> promiseCandidateDateList = new ArrayList<>();
}
