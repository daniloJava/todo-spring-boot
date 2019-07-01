package br.com.todoservice.domain.entity;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document("todo")
public class Todo extends AbstractEntity<String> {

	private String description;
	private String status;

	@Field("_createdDate")
	@CreatedDate
	private Date createdDate;

	@Field("_lastModifiedDate")
	@LastModifiedDate
	private Date lastModifiedDate;

}
