package br.com.todoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.todoservice.domain.dto.TodoFilterDTO;
import br.com.todoservice.domain.entity.Todo;
import br.com.todoservice.exception.TodoNotFoundException;
import br.com.todoservice.repository.TodoRepository;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;

	public Long count() {
		return todoRepository.count();
	}

	public Todo create(final Todo example) {
		return todoRepository.save(example);
	}

	public void delete(final String id) {
		if (!todoRepository.existsById(id)) {
			throw new TodoNotFoundException();
		}

		todoRepository.deleteById(id);
	}

	public Page<Todo> findByFilter(final TodoFilterDTO todo, Pageable pageable) {
		return todoRepository.findByFilter(todo, pageable);
	}

	public Todo get(final String id) {
		return todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
	}

	public Todo update(final Todo todo) {
		if (!todoRepository.existsById(todo.getId())) {
			throw new TodoNotFoundException();
		}

		return todoRepository.save(todo);
	}

}
