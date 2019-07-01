package br.com.todoservice.exception;

public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundException() {
		super("br.gov.sp.prodesp.ssp.dipol.exception.RECORD_NOT_FOUND");
	}

	public RecordNotFoundException(Exception exception) {
		super(exception);
	}

	public RecordNotFoundException(String message) {
		super(message);
	}

	public RecordNotFoundException(String message, Exception exception) {
		super(message, exception);
	}

}
