package br.com.todoservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import springfox.documentation.annotations.ApiIgnore;

import br.com.todoservice.domain.dto.CounterDTO;
import br.com.todoservice.domain.dto.IdentifierDTO;
import br.com.todoservice.domain.dto.TodoDTO;
import br.com.todoservice.domain.dto.TodoFilterDTO;
import br.com.todoservice.domain.entity.Todo;
import br.com.todoservice.service.TodoService;

@RestController
@RequestMapping(path = "/todo", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Api(tags = { "Todo" })
public class TodoController extends AbstractController {

	@SuppressWarnings("squid:S1075")
	private static final String ID_PATH_VARIABLE = "/{id:^(?!count).+}";

	@Autowired
	private TodoService todoService;

	@GetMapping("/count")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Receber o total de Todo", response = CounterDTO.class)
	public CounterDTO count() {
		return new CounterDTO(todoService.count());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Criar nova todo", response = IdentifierDTO.class)
	public IdentifierDTO create(@RequestBody @Valid final TodoDTO example) {
		return new IdentifierDTO(todoService.create(convertToEntity(example, Todo.class)).getId());
	}

	@DeleteMapping(ID_PATH_VARIABLE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deletar todo por ID")
	public void delete(@ApiParam(value = "Identificador Unico", required = true) @PathVariable final String id) {
		todoService.delete(id);
	}

	@GetMapping(ID_PATH_VARIABLE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Recuperar todo pelo id")
	public TodoDTO get(@ApiParam(value = "Identificador Unico", required = true) @PathVariable final String id) {
		return convertToDTO(todoService.get(id), TodoDTO.class);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Recuperar Todo pelos filters")
	@ApiImplicitParams({ //
			@ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Resultados por pagina (0..N)"), //
			@ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Numero dos Todos por pagina") //
	})
	public Page<TodoDTO> search(@Valid final TodoFilterDTO todo, @ApiIgnore Pageable pageable) {
		return convertToDTO(todoService.findByFilter(todo, pageable), TodoDTO.class);
	}

	@PutMapping(value = ID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Atualizar todo pelo ID")
	public void update(@RequestBody @Valid final TodoDTO todo, @PathVariable final String id) {
		todoService.update(convertToEntity(todo, id, Todo.class));
	}

}
