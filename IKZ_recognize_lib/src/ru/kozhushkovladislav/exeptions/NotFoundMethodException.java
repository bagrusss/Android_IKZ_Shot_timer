package ru.kozhushkovladislav.exeptions;

public class NotFoundMethodException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2301006L;

	public NotFoundMethodException() {
		
	}

	public NotFoundMethodException(String detailMessage) {
		super(detailMessage);		
	}

}
