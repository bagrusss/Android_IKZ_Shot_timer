package ru.kozhushkovladislav.exeptions;


/**
 * IncorrectConfigurationsException
 */
public class IncorrectConfigurationsException extends RuntimeException {


	private static final long serialVersionUID = 2301009L;

	public IncorrectConfigurationsException() {
		
	}
	
	public IncorrectConfigurationsException(String detailMessage) {
		super(detailMessage);		
	}

}
