package com.suresh.projects.movieworld.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ApiException extends Exception {
	String message;
	HttpStatus status;
	
	public ApiException(String message, HttpStatus status) {
		super(message);
		this.message = message;
		this.status = status;
	}
	
}
