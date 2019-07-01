package br.com.todoservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "IdentifierDTO", description = "Identificador do Objeto.")
public class IdentifierDTO {

	@ApiModelProperty(value = "Id")
	private String id;

}
