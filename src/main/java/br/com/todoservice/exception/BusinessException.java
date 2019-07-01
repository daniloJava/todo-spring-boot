package br.com.todoservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;

	public BusinessException(Exception exception) {
		super(exception);
	}

	public BusinessException(Exception exception, HttpStatus httpStatus) {
		super(exception);
		this.httpStatus = httpStatus;
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Exception exception) {
		super(message, exception);
	}

	public BusinessException(String message, Exception exception, HttpStatus httpStatus) {
		super(message, exception);
		this.httpStatus = httpStatus;
	}

	public BusinessException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

}