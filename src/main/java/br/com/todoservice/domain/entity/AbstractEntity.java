package br.com.todoservice.domain.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;

@Getter
@Setter
public abstract class AbstractEntity<I extends Serializable> {

	@Id
	@SuppressWarnings("null")
	protected I id;

}
