package com.gc.api.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gc.api.common.base.BaseEntity;
import com.gc.api.common.enums.MemberRole;
import com.gc.api.common.enums.SocialProvider;
import com.gc.api.group.domain.mapping.GroupLikes;
import com.gc.api.group.domain.mapping.GroupMember;
import com.gc.api.member.domain.mapping.MemberEvent;
import com.gc.api.member.domain.mapping.MemberLabel;

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
@Table(name = "members")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String nickname;

	private boolean notification_enable;

	private boolean monday_start;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SocialProvider socialProvider;

	@Column(nullable = false)
	private String profileImage;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(10) DEFAULT 'USER'")
	private MemberRole memberRole;

	private boolean calendarLink;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<DeviceToken> deviceTokenList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<MemberLabel> memberLabelList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<MemberEvent> memberEventList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<GroupLikes> groupLikesList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<GroupMember> groupMemberList = new ArrayList<>();
}
