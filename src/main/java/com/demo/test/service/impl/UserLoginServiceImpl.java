package com.demo.test.service.impl;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.Cacheable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.demo.test.dao.UserRepository;
import com.demo.test.dto.AppConstant;
import com.demo.test.dto.LoginRequest;
import com.demo.test.dto.LoginResponse;
import com.demo.test.dto.RegisterRequest;
import com.demo.test.dto.RegisterResponse;
import com.demo.test.exception.InputValidationException;
import com.demo.test.model.User;
import com.demo.test.service.UserLoginService;
import com.demo.test.utils.PasswordHashing;
@Component
public class UserLoginServiceImpl implements UserLoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserLoginServiceImpl(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	private void isNullOrEmpty(String value,String code) throws InputValidationException {
		if(value.isEmpty()) {
			throw new InputValidationException(code);
		}
	}
	
	@Override
	public ResponseEntity<LoginResponse> signIn(LoginRequest loginRequest) throws InputValidationException{
		try {	
			this.isNullOrEmpty(loginRequest.getUserName(), AppConstant.USR_EMT);
			this.isNullOrEmpty(loginRequest.getPwd(), AppConstant.PWD_EMT);
			this.isNullOrEmpty(loginRequest.getToken(), AppConstant.EMPTY_TOKEN);

			LoginResponse loginResponse = new LoginResponse();

			Optional<User> user = userRepository.findByUserNameAndPwdAndToken(loginRequest.getUserName(),PasswordHashing.getSaltedHash(loginRequest.getPwd()),loginRequest.getToken());
			if(user.isPresent()) {
				loginResponse.setMessage("Login Successful");
				loginResponse.setStatus(Boolean.TRUE);
				loginResponse.setToken(user.get().getToken());
			
				return new ResponseEntity<LoginResponse>(loginResponse,HttpStatus.OK);
			}else {
				loginResponse.setStatus(Boolean.FALSE);
				loginResponse.setMessage("Invalid Credentials");
				return new ResponseEntity<LoginResponse>(loginResponse,HttpStatus.OK);

			}
			
		}catch(Exception e) {
			e.getLocalizedMessage();
			throw new InputValidationException(AppConstant.USR_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<RegisterResponse> signUp(RegisterRequest registerRequest) throws InputValidationException{
		try {
			this.isNullOrEmpty(registerRequest.getUserName(), AppConstant.USR_EMT);
			this.isNullOrEmpty(registerRequest.getPwd(), AppConstant.PWD_EMT);
		
			Optional<User> existingUser = userRepository.findByUserName(registerRequest.getUserName());
		
			if(existingUser.isPresent()) {
				throw new InputValidationException(AppConstant.USR_EXISTS);
	
			}else {
				User user = new User();
				user.setUserName(registerRequest.getUserName());
				user.setPwd(PasswordHashing.getSaltedHash(registerRequest.getPwd()));
				user.setToken(UUID.randomUUID().toString());
				userRepository.save(user);
				
				RegisterResponse registerResponse = new RegisterResponse();
				registerResponse.setStatus("OK");
				return new ResponseEntity<RegisterResponse>(registerResponse,HttpStatus.OK);
			}
		}catch(Exception e) {
			throw new InputValidationException("Something Went Wrong!!!");
		}
		
	}

	
}
