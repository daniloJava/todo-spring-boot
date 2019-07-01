package br.com.todoservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import br.com.todoservice.domain.enumeration.StatusTodoEnum;
import br.com.todoservice.domain.validation.Enum;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ApiModel(value = "TodoFilterDTO", description = "Filter para consulta")
public class TodoFilterDTO {

	@ApiModelProperty(value = "Descrição colocana na descrição 'String'", example = "Descrição")
	private String description;

	@Enum(enumClass = StatusTodoEnum.class, message = "Valores validos são completed ou pending")
	@ApiModelProperty(value = "Descrição do Status são validos completed ou pending", example = "completed")
	private String status;

}
