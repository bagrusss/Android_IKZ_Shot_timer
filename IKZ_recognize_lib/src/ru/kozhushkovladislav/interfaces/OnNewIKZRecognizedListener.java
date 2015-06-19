package ru.kozhushkovladislav.interfaces;

import ru.kozhushkovladislav.ikz_recognize_lib.IKZRecognizeResult;
/*
 Интерфейс для вызова метода при подсчете определенного количества выстрелов
 */

public interface OnNewIKZRecognizedListener {	
	void OnNewIKZRecognized(IKZRecognizeResult result);
}
