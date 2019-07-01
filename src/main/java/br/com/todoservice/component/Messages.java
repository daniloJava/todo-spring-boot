package br.com.todoservice.component;

import lombok.Getter;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import br.com.todoservice.constant.LocaleConstant;

@Component
public class Messages {

	public static final String BASE_NAME = "br.com.todoservice";

	@Getter
	private MessageSource source;

	@Getter
	private MessageSourceAccessor accessor;

	public Messages(MessageSource source) {
		this.source = source;
		accessor = new MessageSourceAccessor(source, LocaleConstant.BRAZIL);
	}

	public String get(Exception exception) {
		if (exception == null) {
			return null;
		}

		if (exception.getMessage() != null && exception.getMessage().contains(BASE_NAME)) {
			return get(exception.getMessage());
		}

		return exception.getLocalizedMessage();
	}

	public String get(String code) {
		return accessor.getMessage(code);
	}

}
