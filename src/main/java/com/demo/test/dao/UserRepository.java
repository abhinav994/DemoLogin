package com.demo.test.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.test.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

	Optional<User> findByUserName(String userName);

	Optional<User> findByUserNameAndPwdAndToken(String useName, String pwd, String token);

}
