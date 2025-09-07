package com.gc.api.member.domain.mapping;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gc.api.common.base.BaseEntity;
import com.gc.api.common.enums.EventReminder;
import com.gc.api.common.enums.EventRepeat;
import com.gc.api.member.domain.Member;

import jakarta.persistence.CascadeType;
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
@Table(name = "member_events")
public class MemberEvent extends BaseEntity {

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
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_label_id")
	private MemberLabel memberLabel;

	@OneToMany(mappedBy = "memberEvent", cascade = CascadeType.ALL)
	@Builder.Default
	private List<MemberEventSharing> memberEventSharingList = new ArrayList<>();
}
