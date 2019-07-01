package br.com.todoservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "CounterDTO", description = "Contando total de registros sem filtros.")
public class CounterDTO {

	@ApiModelProperty(value = "Total")
	private Long total;

	public CounterDTO(Long total) {
		super();
		this.total = total;
	}

}
