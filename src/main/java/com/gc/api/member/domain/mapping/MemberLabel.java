package com.gc.api.member.domain.mapping;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gc.api.common.base.BaseEntity;
import com.gc.api.group.domain.mapping.GroupEvent;
import com.gc.api.label.domain.Label;
import com.gc.api.member.domain.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "member_labels")
public class MemberLabel extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ColumnDefault("true")
	private boolean publicStatus;

	@Column(nullable = false) // unique for {member_id, name}
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "label_id")
	private Label label;

	@OneToMany(mappedBy = "memberLabel", cascade = CascadeType.ALL)
	@Builder.Default
	private List<MemberEvent> memberEventList = new ArrayList<>();

	@OneToMany(mappedBy = "memberLabel", cascade = CascadeType.ALL)
	@Builder.Default
	private List<GroupEvent> groupEventList = new ArrayList<>();
}
