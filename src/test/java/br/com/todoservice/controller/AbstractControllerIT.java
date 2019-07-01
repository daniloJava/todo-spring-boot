package br.com.todoservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;

import br.com.todoservice.TodoServiceSpringBootApplication;
import br.com.todoservice.constant.LocaleConstant;
import br.com.todoservice.container.DefaultMongoDbContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TodoServiceSpringBootApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public abstract class AbstractControllerIT {

	public static final Faker faker = new Faker(LocaleConstant.BRAZIL);

	static {
		if (DefaultMongoDbContainer.isEnabled()) {
			DefaultMongoDbContainer.getInstance().start();
		}
	}

	@Autowired
	protected ModelMapper modelMapper;

	protected <D, T> List<D> convertToDTO(final Iterable<T> models, final Class<D> dtoClass) {
		List<D> dtos = new ArrayList<>();
		for (T model : models) {
			dtos.add(modelMapper.map(model, dtoClass));
		}

		return dtos;
	}

	protected <D, T> D convertToDTO(final T model, final Class<D> dtoClass) {
		return modelMapper.map(model, dtoClass);
	}

	protected HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}

	protected org.springframework.http.HttpEntity<?> getRequestEntity() {
		return new org.springframework.http.HttpEntity<>(getHeaders());
	}

	protected <T> org.springframework.http.HttpEntity<T> getRequestEntity(T object) {
		return new org.springframework.http.HttpEntity<>(object, getHeaders());
	}

}
