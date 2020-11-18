package com.demo.test.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.test.dto.LoginRequest;
import com.demo.test.dto.LoginResponse;
import com.demo.test.dto.RegisterRequest;
import com.demo.test.dto.RegisterResponse;
import com.demo.test.exception.InputValidationException;

@Service
public interface UserLoginService {

	ResponseEntity<LoginResponse> signIn(LoginRequest loginRequest) throws InputValidationException;

	ResponseEntity<RegisterResponse> signUp(RegisterRequest registerRequest) throws InputValidationException;

}
