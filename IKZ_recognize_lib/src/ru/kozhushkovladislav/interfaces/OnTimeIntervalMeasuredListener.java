package ru.kozhushkovladislav.interfaces;

import ru.kozhushkovladislav.ikz_recognize_lib.IKZRecognizeResult;


//интерфейс для подсчета времени между IKZ
public interface OnTimeIntervalMeasuredListener {	
	
	public void OnIntervalMeasured(IKZRecognizeResult result);	
	
}
