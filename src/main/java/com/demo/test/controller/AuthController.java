package com.demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.test.dto.LoginRequest;
import com.demo.test.dto.LoginResponse;
import com.demo.test.dto.RegisterRequest;
import com.demo.test.dto.RegisterResponse;
import com.demo.test.exception.InputValidationException;
import com.demo.test.service.UserLoginService;

@RestController
@RequestMapping("/demo")
public class AuthController {

	@Autowired
	private UserLoginService userLoginService;
	
	public AuthController(UserLoginService userLoginService) {
		this.userLoginService=userLoginService;
	}
	
	@PostMapping(value="/signIn")
	ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest loginRequest) throws InputValidationException{
		return userLoginService.signIn(loginRequest);
	}
	
	@PostMapping(value="/signUp")
	ResponseEntity<RegisterResponse> signUp(@RequestBody RegisterRequest registerRequest) throws InputValidationException{
		return userLoginService.signUp(registerRequest);
	}
	
	
}
