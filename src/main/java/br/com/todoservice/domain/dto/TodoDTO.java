package br.com.todoservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ApiModel(value = "TodoDTO", description = "Todo retornada por padrão")
public class TodoDTO extends AbstractTodoDTO {

	@ApiModelProperty(value = "Identificador Unico", accessMode = AccessMode.READ_ONLY)
	private String id;

}
