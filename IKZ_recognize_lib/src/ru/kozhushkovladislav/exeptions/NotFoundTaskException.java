package ru.kozhushkovladislav.exeptions;

public class NotFoundTaskException extends RuntimeException {

	/**
	 Исключение появляется если неверно указана задача
	 */
	private static final long serialVersionUID = 2301007L;

	public NotFoundTaskException() {
	
	}

	public NotFoundTaskException(String detailMessage) {
		super(detailMessage);
	
	}

}
