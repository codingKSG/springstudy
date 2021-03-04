package com.cos.myjpa.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

	// 1. namingQuery
	
	// select * from user where username = ?1
//	User findByUsername(String username);
	
	// select * from user where username = ?1 and password = ?2
	User findByUsernameAndPassword(String username, String password);
	
	// 2. nativeQuery
	@Query(value = "select * from user where username = ?1 and password = ?2", nativeQuery = true)
	User mLogin(String username, String password);
	
	// 3. 동적쿼리 라이브러리 QueryDSL
}