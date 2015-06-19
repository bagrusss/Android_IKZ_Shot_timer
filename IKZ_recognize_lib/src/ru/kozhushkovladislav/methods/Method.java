package ru.kozhushkovladislav.methods;

import android.media.AudioRecord;

/*
 Паттерн стратегия  
 */
public class Method {
	
	public Method(){
		
	}
	
	protected AudioRecord mAudioRecord;
	protected boolean is_init; 
	
	public AudioRecord getRecorder(){
		return mAudioRecord;
	}
	
	public void init(){
		
		is_init=true;
	}
	public boolean is_init(){
		return is_init;
	}
	//потомки переопределяют метод для распознавания
	public  void listen(){

	}
	
	public void stop(){
		
	}


}
