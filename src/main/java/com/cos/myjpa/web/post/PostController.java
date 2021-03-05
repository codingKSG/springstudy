package com.cos.myjpa.web.post;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.service.PostService;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;

import lombok.RequiredArgsConstructor;

/**
 * ORM(Object Relation Mapping) : 자바 오브젝트를 먼저 만들고 DB에 테이블을 자동 생성,
 * 자바 오브젝트로 연관관계를 맺을 수 있다.
 * JPA = Java 오브젝트를 영구적으로 저장하기 위한 인터페이스(함수)
 */

@RequiredArgsConstructor
@RestController
public class PostController {
	
	private final PostService postService;
	private final HttpSession session;

	@PostMapping("/post")
	public CommonRespDto<?> save(@RequestBody PostSaveReqDto postSaveReqDto) { // title, content
		
		User principal = (User)session.getAttribute("principal");
		
		if(principal == null) {
			return new CommonRespDto<>(-1, "실패", null);
		}		
		
		return new CommonRespDto<>(1, "성공",postService.save(postSaveReqDto, principal));
	}
	
	@GetMapping("/post")
	public CommonRespDto<?> findAll() {				
		return new CommonRespDto<>(1, "성공", postService.findAll());
	}
	
	@GetMapping("/post/{id}")
	public CommonRespDto<?> findById(@PathVariable Long id) {		
		// MessageConverter가 모든 getter를 다 호출해서 JSON으로 만들어 준다.
		return new CommonRespDto<>(1, "성공", postService.findById(id));
	}
	
	@PutMapping("/post/{id}")
	public CommonRespDto<?> update(@PathVariable Long id, @RequestBody PostUpdateReqDto postUpdateReqDto) {
		
//		Post p = new Post();
//		em.persist(p);
//		em.createNativeQuery("select * from post");
		
		return new CommonRespDto<>(1, "성공", postService.update(id, postUpdateReqDto));
	}
	
	@DeleteMapping("/post/{id}")
	public CommonRespDto<?> deleteById(@PathVariable Long id) {
		
		postService.delete(id);
		
		return new CommonRespDto<>(1, "성공", null);
	}
	
}
