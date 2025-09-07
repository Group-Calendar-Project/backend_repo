package com.gc.api.group.domain.mapping;

import java.time.LocalTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gc.api.common.base.BaseEntity;
import com.gc.api.common.enums.EventReminder;
import com.gc.api.common.enums.EventRepeat;
import com.gc.api.group.domain.Group;
import com.gc.api.member.domain.mapping.MemberLabel;
import com.gc.api.promise.domain.mapping.GroupPromise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "group_events")
public class GroupEvent extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	private LocalTime startTime;

	private LocalTime endTime;

	private boolean allDay; // allDay가 false인 경우, startTime과 endTime이 NOT NULL이도록 검증해야 함

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(10) DEFAULT 'NONE'")
	private EventRepeat eventRepeat;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(10) DEFAULT 'NONE'")
	private EventReminder eventReminder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_label_id")
	private MemberLabel memberLabel;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_promise_id")
	private GroupPromise groupPromise;
}
