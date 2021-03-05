package com.cos.myjpa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.cos.myjpa.web.user.dto.UserRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public List<UserRespDto> findAll() {
		List<User> usersEntity = userRepository.findAll();

//		List<UserRespDto> userRespDtos = new ArrayList<>();
//		for (int i = 0; i < usersEntity.size(); i++) {
//			userRespDtos.add(new UserRespDto(usersEntity.get(i)));
//		}
//		for (User user : usersEntity) {
//			userRespDtos.add(new UserRespDto(user));
//		}
//		usersEntity.stream().forEach((u)->{
//			userRespDtos.add(new UserRespDto(u));
//		});

		// java stream map
		List<UserRespDto> userRespDtos = usersEntity.stream().map((u) -> {
			return new UserRespDto(u);
		}).collect(Collectors.toList());

		return userRespDtos;
	}

	@Transactional(readOnly = true)
	public UserRespDto findById(long id) {
		// 옵셔널 get(), orElseGet(), orElseThrow
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 데이터를 찾을 수 없습니다.");
		});

		UserRespDto userRespDto = new UserRespDto(userEntity);
		return userRespDto;
	}

	@Transactional(readOnly = true)
	public User profile(long id) {
		// 옵셔널 get(), orElseGet(), orElseThrow
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 데이터를 찾을 수 없습니다.");
		});
		return userEntity;
	}
	
	// 쓰기는 트렌젝션을 발동 시켜야함
	// 다른 user가 동시접근을 못하도록
	@Transactional
	public User join(UserJoinReqDto userJoinReqDto) {
		User userEntity = userRepository.save(userJoinReqDto.toEntity());
		return userEntity;
	}

	@Transactional(readOnly = true)
	public User login(UserLoginReqDto userLoginReqDto) {
		User userEntity = userRepository.findByUsernameAndPassword(userLoginReqDto.getUsername(),
				userLoginReqDto.getPassword());
		
		return userEntity;
	}

	
}
