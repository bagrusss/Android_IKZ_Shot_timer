package ru.kozhushkovladislav.exeptions;

public class NotFoundTaskException extends RuntimeException {

	/**
	 ���������� ���������� ���� ������� ������� ������
	 */
	private static final long serialVersionUID = 2301007L;

	public NotFoundTaskException() {
	
	}

	public NotFoundTaskException(String detailMessage) {
		super(detailMessage);
	
	}

}
