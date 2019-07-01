package br.com.todoservice.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.todoservice.domain.entity.AbstractEntity;

public class AbstractController {

	@Autowired
	protected ModelMapper modelMapper;

	protected <D, T> List<D> convertToDTO(final Iterable<T> models, final Class<D> dtoClass) {
		List<D> dtos = new ArrayList<>();
		for (T model : models) {
			dtos.add(modelMapper.map(model, dtoClass));
		}

		return dtos;
	}

	protected <D, T> Page<D> convertToDTO(final Page<T> page, final Class<D> dtoClass) {
		return new PageImpl<>(convertToDTO(page.getContent(), dtoClass), page.getPageable(), page.getTotalElements());
	}

	protected <D, T> D convertToDTO(final T model, final Class<D> dtoClass) {
		return modelMapper.map(model, dtoClass);
	}

	protected <D, E extends AbstractEntity<I>, I extends Serializable> E convertToEntity(final D dto, final Class<E> entityClass) {
		return modelMapper.map(dto, entityClass);
	}

	@SuppressWarnings("null")
	protected <D, E extends AbstractEntity<I>, I extends Serializable> E convertToEntity(final D dto, final I id, final Class<E> entityClass) {
		E entity = modelMapper.map(dto, entityClass);
		entity.setId(id);

		return entity;
	}

}
