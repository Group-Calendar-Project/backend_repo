package com.gc.api.group.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gc.api.common.base.BaseEntity;
import com.gc.api.group.domain.mapping.GroupEvent;
import com.gc.api.group.domain.mapping.GroupLikes;
import com.gc.api.group.domain.mapping.GroupMember;
import com.gc.api.promise.domain.mapping.GroupPromise;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "`groups`")
public class Group extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column()
	private String profileImage;

	@Column()
	private String description;

	@Column(nullable = false)
	private String link;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	@Builder.Default
	private List<GroupLikes> groupLikesList = new ArrayList<>();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	@Builder.Default
	private List<GroupMember> groupMemberList = new ArrayList<>();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	@Builder.Default
	private List<GroupEvent> groupEventList = new ArrayList<>();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	@Builder.Default
	private List<GroupPromise> groupPromiseList = new ArrayList<>();
}
