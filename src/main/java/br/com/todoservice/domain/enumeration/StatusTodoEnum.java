package br.com.todoservice.domain.enumeration;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;

@Getter
public enum StatusTodoEnum {

	COMPLETED(1, "completed"), //
	PENDING(2, "pending");

	private static final Map<Integer, StatusTodoEnum> LOOKUP = new HashMap<>();
	static {
		for (StatusTodoEnum e : StatusTodoEnum.values()) {
			LOOKUP.put(e.getId(), e);
		}
	}

	@JsonCreator
	public static StatusTodoEnum create(Integer value) {
		return valueOfId(value);
	}

	public static StatusTodoEnum valueOfId(Integer id) {
		return id != null ? LOOKUP.get(id) : null;
	}

	private final int id;

	private final String descricao;

	StatusTodoEnum(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

}
