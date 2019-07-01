package br.com.todoservice.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

@Configuration
public class DefaultFormatterConfiguration {

	@Bean
	public Formatter<LocalDate> localDateFormatter() {
		return new Formatter<LocalDate>() {

			@Override
			public LocalDate parse(String text, Locale locale) {
				return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
			}

			@Override
			public String print(LocalDate object, Locale locale) {
				return DateTimeFormatter.ISO_DATE.format(object);
			}
		};
	}

	@Bean
	public Formatter<LocalDateTime> localDateTimeFormatter() {
		return new Formatter<LocalDateTime>() {

			@Override
			public LocalDateTime parse(String text, Locale locale) {
				return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
			}

			@Override
			public String print(LocalDateTime object, Locale locale) {
				return DateTimeFormatter.ISO_DATE_TIME.format(object);
			}
		};
	}

	@Bean
	public Formatter<LocalTime> localTimeFormatter() {
		return new Formatter<LocalTime>() {

			@Override
			public LocalTime parse(String text, Locale locale) {
				return LocalTime.parse(text, DateTimeFormatter.ISO_TIME);
			}

			@Override
			public String print(LocalTime object, Locale locale) {
				return DateTimeFormatter.ISO_TIME.format(object);
			}
		};
	}

}
