package br.com.todoservice.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class DefaultAppConfiguration {

	@Getter
	@Setter
	public static class Document {

		@Getter
		@Setter
		public static class Contact {

			@NotEmpty
			private String name;

			@NotEmpty
			private String email;

			@NotEmpty
			private String url;
		}

		@Valid
		private final Contact contact = new Contact();

		@NotEmpty
		private String title;

		@NotEmpty
		private String description;

		@NotEmpty
		private String version;

		@NotEmpty
		private String termsOfServiceUrl;

		@NotEmpty
		private String license;

		@NotEmpty
		private String licenseUrl;
	}

	@Valid
	private final Document document = new Document();

}
