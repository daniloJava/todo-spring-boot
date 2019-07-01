package br.com.todoservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.todoservice.domain.entity.Todo;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String>, TodoRepositoryCustom {

}
