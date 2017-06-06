package com.suresh.projects.movieworld.exceptions;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ApiError implements Serializable {
	private String message;
	private int status;

	public ApiError(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
