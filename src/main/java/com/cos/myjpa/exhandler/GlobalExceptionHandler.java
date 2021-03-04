package com.cos.myjpa.exhandler;

import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cos.myjpa.web.dto.CommonRespDto;

@RestControllerAdvice // 모든 익셉션을 낚아챔.
public class GlobalExceptionHandler {

	// 그 중 IllegalArgumentException이 발생하면 해당 함수 실행됨.
	@ExceptionHandler(value = SQLException.class)
	public CommonRespDto<?> sqlException(Exception e) {
		return new CommonRespDto<>(-1, e.getMessage(), null);
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public CommonRespDto<?> illegalArgument(Exception e) {
		return new CommonRespDto<>(-1, e.getMessage(), null);
	}
	
	@ExceptionHandler(value = EmptyResultDataAccessException.class)
	public CommonRespDto<?> emptyResultDataAccess(Exception e) {
		return new CommonRespDto<>(-1, e.getMessage(), null);
	}
}
