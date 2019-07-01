package br.com.todoservice.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Builder
public class ErrorResponse {

	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();

	private String message;
	private String details;

	@JsonInclude(value = Include.NON_NULL)
	private List<FieldError> fieldErros;

}
