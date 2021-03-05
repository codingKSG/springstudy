package com.cos.myjpa.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.myjpa.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User { // User 1 <-> Post N
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String email;
	
	@CreationTimestamp // 자동으로 현재 시간이 들어감.
	private LocalDateTime createDate;
	
	// DB에서 User를 select할 때 조인을 통해 
	// 테이블에 컬럼을 생성하지 않기위해
	// FK의 변수명을 mappedBy에 넣어 FK의 주인이 아님을 알려줌
	// FK는 user 변수명임
	// 역방향 맵핑
	@JsonIgnoreProperties({"user"})
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)// 기본전략 = LAZY
	private List<Post> post;
	
//	@Transient // 테이블에 컬럼을 안만들어줌
}