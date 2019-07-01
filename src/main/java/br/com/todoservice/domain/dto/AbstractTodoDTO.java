package br.com.todoservice.domain.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import io.swagger.annotations.ApiModelProperty;

import br.com.todoservice.domain.enumeration.StatusTodoEnum;
import br.com.todoservice.domain.validation.Enum;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractTodoDTO {

	@NotNull
	@ApiModelProperty(value = "Descrição da Todo do tipo 'String'", example = "Descrição", required = true)
	private String description;

	@Enum(enumClass = StatusTodoEnum.class, message = "Valores validos são completed ou pending")
	@ApiModelProperty(value = "Descrição do Status são validos: completed ou pending", example = "pending")
	private String status;

}
