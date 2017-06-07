package com.suresh.projects.movieworld.exceptions;

import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<Object> handleApiException(final ApiException ex, final WebRequest request) {
		return handleExceptionInternal(ex, message(ex.status, ex), new HttpHeaders(), ex.status, request);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleApiException(final IllegalArgumentException ex, final WebRequest request) {
		return handleExceptionInternal(ex, message(HttpStatus.PRECONDITION_FAILED, ex), new HttpHeaders(), HttpStatus.PRECONDITION_FAILED, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private ApiError message(final HttpStatus status, final Exception ex) {
		final String message = isEmpty(ex.getMessage()) ? ex.getClass().getSimpleName() : ex.getMessage();
		return new ApiError(status.value(), message);
	}
	
}
