package br.com.todoservice.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.todoservice.domain.ErrorResponse;
import br.com.todoservice.domain.FieldError;
import br.com.todoservice.domain.RestResponsePage;
import br.com.todoservice.domain.dto.CounterDTO;
import br.com.todoservice.domain.dto.IdentifierDTO;
import br.com.todoservice.domain.dto.TodoDTO;
import br.com.todoservice.domain.entity.Todo;
import br.com.todoservice.repository.TodoRepository;

@DisplayName("Todo Controller")
public class TodoControllerIT extends AbstractControllerIT {

	@Nested
	@DisplayName("GET /todo/count")
	class Counter {

		StringBuilder url = new StringBuilder(RESOURCE_BASE_URL).append("/count");

		@Test
		@DisplayName("should return the total of examples with status code 200")
		public void testCount() {
			List<TodoDTO> examples = createTodo();

			ResponseEntity<CounterDTO> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), CounterDTO.class);

			assertNotNull(responseGet);
			assertNotNull(responseGet.getBody());
			assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
			assertThat(responseGet.getBody().getTotal(), equalTo(Long.valueOf(examples.size())));
		}

		@Test
		@DisplayName("should return zero with status code 200 when no examples are found")
		public void testCountWhenNoResult() {
			ResponseEntity<CounterDTO> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), CounterDTO.class);

			assertNotNull(responseGet);
			assertNotNull(responseGet.getBody());
			assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
			assertThat(responseGet.getBody().getTotal(), equalTo(0L));
		}

	}

	@Nested
	@DisplayName("DELETE /todo/:id")
	class Delete {

		@Test
		@DisplayName("should delete a example and return 204 as status code")
		public void testDelete() {
			TodoDTO example = createExample();

			StringBuilder url = new StringBuilder(RESOURCE_BASE_URL).append("/").append(example.getId());

			ResponseEntity<Void> responseDelete = restTemplate.exchange(url.toString(), HttpMethod.DELETE, getRequestEntity(example), Void.class);

			assertNotNull(responseDelete);
			assertNull(responseDelete.getBody());
			assertThat(responseDelete.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

			ResponseEntity<Void> responseGet = restTemplate.exchange(RESOURCE_BASE_URL + "/" + example.getId(), HttpMethod.GET, getRequestEntity(), Void.class);

			assertNotNull(responseGet);
			assertNull(responseGet.getBody());
			assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
		}

		@Test
		@DisplayName("should return status code 404 when no record is found")
		public void testDeleteWhenRecordNotFound() {
			StringBuilder url = new StringBuilder(RESOURCE_BASE_URL).append("/").append(System.currentTimeMillis());

			ResponseEntity<Void> responseDelete = restTemplate.exchange(url.toString(), HttpMethod.DELETE, getRequestEntity(), Void.class);

			assertNotNull(responseDelete);
			assertNull(responseDelete.getBody());
			assertThat(responseDelete.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
		}

	}

	@Nested
	@DisplayName("GET /todo")
	class Get {

		@Nested
		@DisplayName("should return a list of examples with status code 200")
		class GetSuccess {

			@Test
			@DisplayName("when all filters are provided")
			public void testSearchWhenAllParameters() {
				List<TodoDTO> examples = createTodo();

				Pageable pageable = PageRequest.of(0, 2);

				StringBuilder url = new StringBuilder(RESOURCE_BASE_URL);
				url.append(String.format("?description=%s", examples.get(0).getDescription()));
				url.append(String.format("&status=%s", examples.get(0).getStatus()));
				url.append(String.format("&page=%d", pageable.getPageNumber()));
				url.append(String.format("&size=%d", pageable.getPageSize()));

				ParameterizedTypeReference<RestResponsePage<TodoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<TodoDTO>>() {};

				ResponseEntity<RestResponsePage<TodoDTO>> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), responseType);

				assertNotNull(responseGet);
				assertNotNull(responseGet.getBody());
				assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
				assertThat(responseGet.getBody().getContent(), hasSize(1));
				assertThat(responseGet.getBody().getContent(), hasItem(hasProperty("id", equalTo(examples.get(0).getId()))));

				for (TodoDTO response : responseGet.getBody().getContent()) {
					assertEqualsToExample(examples.stream().filter(a -> a.getId().equals(response.getId())).findFirst().get(), response);
				}
			}

			@Test
			@DisplayName("when no filter is provided")
			public void testSearchWhenNoFilters() {
				List<TodoDTO> examples = createTodo();

				Pageable pageable = PageRequest.of(0, 2);

				StringBuilder url = new StringBuilder(RESOURCE_BASE_URL);
				url.append(String.format("?page=%d", pageable.getPageNumber()));
				url.append(String.format("&size=%d", pageable.getPageSize()));

				ParameterizedTypeReference<RestResponsePage<TodoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<TodoDTO>>() {};

				ResponseEntity<RestResponsePage<TodoDTO>> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), responseType);

				assertNotNull(responseGet);
				assertNotNull(responseGet.getBody());
				assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
				assertEquals(examples.size(), responseGet.getBody().getTotalElements());
				assertEquals(pageable.getPageSize(), responseGet.getBody().getContent().size());

				for (TodoDTO response : responseGet.getBody().getContent()) {
					assertEqualsToExample(examples.stream().filter(a -> a.getId().equals(response.getId())).findFirst().get(), response);
				}
			}

			@Test
			@DisplayName("when the paging object was not provided")
			public void testSearchWhenNoParameters() {
				List<TodoDTO> examples = createTodo();

				StringBuilder url = new StringBuilder(RESOURCE_BASE_URL);

				ParameterizedTypeReference<RestResponsePage<TodoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<TodoDTO>>() {};

				ResponseEntity<RestResponsePage<TodoDTO>> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), responseType);

				assertNotNull(responseGet);
				assertNotNull(responseGet.getBody());
				assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
				assertThat(responseGet.getBody().getContent(), hasSize(examples.size()));
				assertThat(responseGet.getBody().getContent(), hasItem(hasProperty("id", equalTo(examples.get(0).getId()))));
				assertThat(responseGet.getBody().getContent(), hasItem(hasProperty("id", equalTo(examples.get(1).getId()))));
				assertThat(responseGet.getBody().getContent(), hasItem(hasProperty("id", equalTo(examples.get(2).getId()))));

				for (TodoDTO response : responseGet.getBody().getContent()) {
					assertEqualsToExample(examples.stream().filter(a -> a.getId().equals(response.getId())).findFirst().get(), response);
				}
			}

			@Test
			@DisplayName("when only the filter status is provided")
			public void testSearchWhenOnlyFilterStartIsProvided() {
				List<TodoDTO> examples = createTodo();
				TodoDTO expected = examples.get(1);

				Pageable pageable = PageRequest.of(0, 2);

				StringBuilder url = new StringBuilder(RESOURCE_BASE_URL);
				url.append(String.format("?status=%s", expected.getStatus()));
				url.append(String.format("&page=%d", pageable.getPageNumber()));
				url.append(String.format("&size=%d", pageable.getPageSize()));

				ParameterizedTypeReference<RestResponsePage<TodoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<TodoDTO>>() {};

				ResponseEntity<RestResponsePage<TodoDTO>> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), responseType);

				assertNotNull(responseGet);
				assertNotNull(responseGet.getBody());
				assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
				assertThat(responseGet.getBody().getContent(), hasSize(2));
				assertThat(responseGet.getBody().getContent(), hasItem(hasProperty("id", equalTo(expected.getId()))));
				assertEqualsToExample(expected, responseGet.getBody().getContent().get(1));
			}

		}

		@Test
		@DisplayName("should return a empty list with status code 200 when no examples are found")
		public void testSearchWhenNoResult() {
			Pageable pageable = PageRequest.of(0, 2);

			StringBuilder url = new StringBuilder(RESOURCE_BASE_URL);
			url.append(String.format("?page=%d", pageable.getPageNumber()));
			url.append(String.format("&size=%d", pageable.getPageSize()));

			ParameterizedTypeReference<RestResponsePage<TodoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<TodoDTO>>() {};

			ResponseEntity<RestResponsePage<TodoDTO>> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), responseType);

			assertNotNull(responseGet);
			assertNotNull(responseGet.getBody());
			assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
			assertEquals(0, responseGet.getBody().getTotalElements());
			assertEquals(0, responseGet.getBody().getContent().size());
		}

	}

	@Nested
	@DisplayName("GET /todo/:id")
	class GetById {

		@Test
		@DisplayName("should return a one example with status code 200")
		public void testGetById() {
			TodoDTO example = createExample();

			StringBuilder url = new StringBuilder(RESOURCE_BASE_URL).append("/").append(example.getId());

			ResponseEntity<TodoDTO> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), TodoDTO.class);

			assertNotNull(responseGet);
			assertNotNull(responseGet.getBody());
			assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
			assertEqualsToExample(example, responseGet.getBody());
		}

		@Test
		@DisplayName("should return status code 404 when no record is found")
		public void testGetByIdWhenRecordNotFound() {
			StringBuilder url = new StringBuilder(RESOURCE_BASE_URL).append("/").append(System.currentTimeMillis());

			ResponseEntity<Void> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), Void.class);

			assertNotNull(responseGet);
			assertNull(responseGet.getBody());
			assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
		}

	}

	@Nested
	@DisplayName("POST /todo")
	class Post {

		@Nested
		@DisplayName("should return status code 400")
		class PostFailure {

			@Test
			@DisplayName("when all required fields are missing")
			public void testCreateWhenBadRequest() {
				StringBuilder url = new StringBuilder(RESOURCE_BASE_URL);

				ResponseEntity<ErrorResponse> response = restTemplate.exchange(url.toString(), HttpMethod.POST, getRequestEntity(new TodoDTO()), ErrorResponse.class);

				FieldError expectedDescription = new FieldError("description", "não pode ser nulo");

				assertNotNull(response);
				assertNotNull(response.getBody());
				assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
				assertThat(response.getBody().getMessage(), equalTo("Bad Request"));
				assertThat(response.getBody().getDetails(), equalTo("uri=" + url.toString()));
				assertThat(response.getBody().getTimestamp(), not(nullValue()));
				assertThat(response.getBody().getFieldErros(), hasSize(1));
				assertThat(response.getBody().getFieldErros(), hasItems(expectedDescription));
			}

		}

		@Test
		@DisplayName("should return a new example with status code 201")
		public void testCreate() {
			TodoDTO example = convertToDTO(builderTodo().build(), TodoDTO.class);

			StringBuilder url = new StringBuilder(RESOURCE_BASE_URL);

			ResponseEntity<IdentifierDTO> response = restTemplate.exchange(url.toString(), HttpMethod.POST, getRequestEntity(example), IdentifierDTO.class);

			assertNotNull(response);
			assertNotNull(response.getBody());
			assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
			assertNotNull(response.getBody().getId());
		}

	}

	@Nested
	@DisplayName("PUT /todo/:id")
	class Put {

		@Nested
		@DisplayName("should return status code 400")
		class PutFailure {

			@Test
			@DisplayName("when all required fields are missing")
			public void testUpdateWhenBadRequest() {
				TodoDTO example = createExample();

				StringBuilder url = new StringBuilder(RESOURCE_BASE_URL).append("/").append(example.getId());

				ResponseEntity<ErrorResponse> response = restTemplate.exchange(url.toString(), HttpMethod.PUT, getRequestEntity(new TodoDTO()), ErrorResponse.class);

				FieldError expectedDescription = new FieldError("description", "não pode ser nulo");

				assertNotNull(response);
				assertNotNull(response.getBody());
				assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
				assertThat(response.getBody().getMessage(), equalTo("Bad Request"));
				assertThat(response.getBody().getDetails(), equalTo("uri=" + url.toString()));
				assertThat(response.getBody().getTimestamp(), not(nullValue()));
				assertThat(response.getBody().getFieldErros(), hasSize(1));
				assertThat(response.getBody().getFieldErros(), hasItems(expectedDescription));
			}

		}

		@Test
		@DisplayName("should update the example and return 200 as status code")
		public void testUpdate() {
			TodoDTO example = createExample();
			TodoDTO data = convertToDTO(builderTodo().build(), TodoDTO.class);

			StringBuilder url = new StringBuilder(RESOURCE_BASE_URL).append("/").append(example.getId());

			ResponseEntity<Void> responsePut = restTemplate.exchange(url.toString(), HttpMethod.PUT, getRequestEntity(data), Void.class);

			assertNotNull(responsePut);
			assertNull(responsePut.getBody());
			assertThat(responsePut.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

			ResponseEntity<TodoDTO> responseGet = restTemplate.exchange(url.toString(), HttpMethod.GET, getRequestEntity(), TodoDTO.class);

			assertNotNull(responseGet);
			assertNotNull(responseGet.getBody());
			assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));

			data.setId(example.getId());
			assertEqualsToExample(data, responseGet.getBody());
		}

		@Test
		@DisplayName("should return status code 404 when no record is found")
		public void testUpdateWhenRecordNotFound() {
			TodoDTO example = convertToDTO(builderTodo().build(), TodoDTO.class);

			StringBuilder url = new StringBuilder(RESOURCE_BASE_URL).append("/").append(System.currentTimeMillis());

			ResponseEntity<Void> responsePut = restTemplate.exchange(url.toString(), HttpMethod.PUT, getRequestEntity(example), Void.class);

			assertNotNull(responsePut);
			assertNull(responsePut.getBody());
			assertThat(responsePut.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
		}

	}

	static final String RESOURCE_BASE_URL = "/todo";

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	TodoRepository exampleRepository;

	void assertEqualsToExample(final TodoDTO expected, final TodoDTO actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getStatus(), actual.getStatus());
	}

	@BeforeEach
	void beforeEach() {
		exampleRepository.deleteAll();
	}

	Todo.TodoBuilder builderTodo() {
		return Todo.builder().description(faker.name().name()).status("pending");
	}

	TodoDTO createExample() {
		return createTodo(builderTodo().build());
	}

	List<TodoDTO> createTodo() {
		List<Todo> examples = new ArrayList<>();

		examples.add(builderTodo().build());
		examples.add(builderTodo().build());
		examples.add(builderTodo().build());
		examples.add(builderTodo().build());
		examples.add(builderTodo().build());
		examples.forEach((example) -> {
			exampleRepository.save(example);

			assertNotNull(example.getId());
		});

		return convertToDTO(examples, TodoDTO.class);
	}

	TodoDTO createTodo(Todo example) {
		example = exampleRepository.save(example);

		assertNotNull(example.getId());

		return convertToDTO(example, TodoDTO.class);
	}

}
