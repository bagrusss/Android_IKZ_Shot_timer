package ru.kozhushkovladislav.interfaces;

import ru.kozhushkovladislav.ikz_recognize_lib.IKZRecognizeResult;

public interface OnIKZRecongizedListener {
	//calls when IKZ had been recognized
	void OnIKZRecongized(IKZRecognizeResult result);
}
