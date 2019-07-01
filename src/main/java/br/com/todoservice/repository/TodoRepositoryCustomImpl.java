package br.com.todoservice.repository;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.todoservice.domain.dto.TodoFilterDTO;
import br.com.todoservice.domain.entity.Todo;

@Repository
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

	@Autowired
	public MongoTemplate mongoTemplate;

	@Override
	public Page<Todo> findByFilter(TodoFilterDTO todo, Pageable pageable) {

		Query query = new Query();

		if (StringUtils.isNotBlank(todo.getDescription())) {
			query.addCriteria(Criteria.where("description").is(todo.getDescription()));
		}

		if (StringUtils.isNotBlank(todo.getStatus())) {
			query.addCriteria(Criteria.where("status").is(todo.getStatus()));
		}

		query.with(pageable);

		Long count = mongoTemplate.count(query, Todo.class);

		if (count == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, count);
		}

		return new PageImpl<>(mongoTemplate.find(query, Todo.class), pageable, count);
	}

}
