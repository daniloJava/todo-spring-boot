package br.com.todoservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class DefaultSwaggerConfiguration {

	private DefaultAppConfiguration configuration;

	public DefaultSwaggerConfiguration(DefaultAppConfiguration configuration) {
		this.configuration = configuration;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2) //
				.select() //
				.apis(RequestHandlerSelectors.any()) //
				.paths(PathSelectors.any()) //
				.build() //
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact( //
				configuration.getDocument().getContact().getName(), //
				configuration.getDocument().getContact().getUrl(), //
				configuration.getDocument().getContact().getEmail());

		return new ApiInfoBuilder() //
				.title(configuration.getDocument().getTitle()) //
				.description(configuration.getDocument().getDescription()) //
				.version(configuration.getDocument().getVersion()) //
				.termsOfServiceUrl(configuration.getDocument().getTermsOfServiceUrl()) //
				.license(configuration.getDocument().getLicense()) //
				.licenseUrl(configuration.getDocument().getLicenseUrl()) //
				.contact(contact) //
				.build();
	}

}
