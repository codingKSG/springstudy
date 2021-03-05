package com.cos.myjpa.web.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.service.UserService;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.cos.myjpa.web.user.dto.UserRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;
	private final HttpSession session;

	@GetMapping("/user")
	public CommonRespDto<?> findAll() {
		List<UserRespDto> dto = userService.findAll();

		return new CommonRespDto<>(1, "성공", dto);
	}

	@GetMapping("/user/{id}")
	public CommonRespDto<?> findById(@PathVariable Long id) {
		UserRespDto dto = userService.findById(id);

		// MessageConverter가 모든 getter를 다 호출해서 JSON으로 만들어 준다.
		return new CommonRespDto<>(1, "성공", dto);
	}
	
	@GetMapping("/user/{id}/post")
	public CommonRespDto<?> profile(@PathVariable Long id) {
		
		User user = userService.profile(id);

		// MessageConverter가 모든 getter를 다 호출해서 JSON으로 만들어 준다.
		return new CommonRespDto<>(1, "성공", user);
	}

	@PostMapping("/join") // auth(인증)
	public CommonRespDto<?> join(@RequestBody UserJoinReqDto userJoinReqDto) {
		
		return new CommonRespDto<>(1, "성공", userService.join(userJoinReqDto));
	}

	@PostMapping("/login")
	public CommonRespDto<?> login(@RequestBody UserLoginReqDto userLoginReqDto) {
		
		User userEntity = userService.login(userLoginReqDto);

		if (userEntity == null) {
			return new CommonRespDto<>(-1, "실패", null);
		} else {
			userEntity.setPassword(null);
			session.setAttribute("principal", userEntity);
			return new CommonRespDto<>(1, "성공", userEntity);
		}
	}

}