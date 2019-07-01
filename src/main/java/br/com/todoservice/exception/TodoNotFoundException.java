package br.com.todoservice.exception;

public class TodoNotFoundException extends RecordNotFoundException {

	private static final long serialVersionUID = 1L;

	public TodoNotFoundException() {
		super("Todo not found");
	}

	public TodoNotFoundException(String message) {
		super(message);
	}

}
