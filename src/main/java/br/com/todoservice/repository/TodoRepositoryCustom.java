package br.com.todoservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.todoservice.domain.dto.TodoFilterDTO;
import br.com.todoservice.domain.entity.Todo;

public interface TodoRepositoryCustom {

	Page<Todo> findByFilter(TodoFilterDTO todo, Pageable pageable);

}
