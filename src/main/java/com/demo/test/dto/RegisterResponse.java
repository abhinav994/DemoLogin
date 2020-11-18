package com.demo.test.dto;

import java.io.Serializable;

public class RegisterResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -908238796311878728L;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
