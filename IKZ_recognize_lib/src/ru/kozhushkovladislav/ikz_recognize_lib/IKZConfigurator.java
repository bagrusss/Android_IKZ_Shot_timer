package ru.kozhushkovladislav.ikz_recognize_lib;

import ru.kozhushkovladislav.exeptions.IncorrectDistanceException;
import ru.kozhushkovladislav.exeptions.NotFoundMethodException;
import ru.kozhushkovladislav.exeptions.NotFoundTaskException;

/*класс настроек
 содержит параметры звукозаписи и использоемый метод */
public class IKZConfigurator {

	public IKZConfigurator() {

	}

	// метод распознавания
	public static final int METHOD_TWO_MICROPHONES = 0x235; 
	public static final int METHOD_UP_LOW_LIMITS = 0x236; 

	// задачи
	public static final int IKZ_MAX_COUNT_TASK = 0x335; 
	public static final int IKZ_RECOGNIZE_TASK = 0x336; 
	public static final int IKZ_TIME_INTERVAL_TASK = 0x337; 
	public static final int IKZ_INFINITY_COUNT_TASK = 0x337;

	private int mDistance_mm = 0; 	// расстояние между  и устройством
	
	private int mMethidId = 0; 		//
	private int mTask = 0; 			// задача распознавания
	private boolean mUseMicSens;
	private double mMicSensDB;
	private int mSample;
	//private int mAudioFormat=AudioFormat.ENCODING_PCM_16BIT;
	//private int mChannel=AudioFormat.CHANNEL_IN_MONO;

	public int getSample() {
		return mSample;
	}

	public void setSample(int mSample) {
		this.mSample = mSample;
	}

	public double getmMicSensDB() {
		return mMicSensDB;
	}

	public void setmMicSensDB(double mMicSensDB) {
		this.mMicSensDB = mMicSensDB;
	}

	public boolean isUseMicSens() {
		return mUseMicSens;
	}

	public void useMicSens(boolean mUseMicSens) {
		this.mUseMicSens = mUseMicSens;
	}

	public boolean isCorrect() {
		return mDistance_mm != 0 || mMethidId != 0 || mTask != 0;
	}

	public int getmDistance_mm() {
		return mDistance_mm;
	}

	/**
	 * Sets the distance between sensor and sound source.
	 * 
	 * @param distance_mm
	 *            must be between 0 and 10000
	 * @throws IncorrectDistanceException
	 */
	
	public IKZConfigurator setmDistance_mm(int distance_mm) {
		if (mDistance_mm >= 0 || mDistance_mm < 10000)
			this.mDistance_mm = distance_mm;
		else {
			mDistance_mm=-1;
			throw new IncorrectDistanceException("IncorrectDistance");
		}
		return this;
	}

	/**
	 * Sets the recognize method.
	 * 
	 * @param method
	 *            can be: <br>
	 *            RECOGNIZE_METHOD_TWO_MICROPHONES <br>
	 *            RECOGNIZE_METHOD_UP_LOW_LIMITS
	 * @throws NotFoundMethodException
	 */
	public void setmMethod(int method) {
		if (method == METHOD_UP_LOW_LIMITS
				|| method == METHOD_TWO_MICROPHONES) {
			this.mMethidId = method;
			return;
		}
		mMethidId = 0;
		throw new NotFoundMethodException("Incorrect method selected");
	}

	public int getmTask() {
		return mTask;
	}

	/**
	 * 
	 * <B>setmTask</B> method sets the task type
	 * 
	 * @param task
	 *            can be: <br>
	 *            IKZ_MAX_COUNT_TASK <br>
	 *            IKZ_RECOGNIZE_TASK <br>
	 *            IKZ_TIME_INTERVAL_TASK <br>
	 *            IKZ_INFINITY_COUNT_TASK
	 *            <p>
	 *            For examle <B>IKZConfigurator.IKZ_MAX_COUNT_TASK</B>
	 * @throws NotFoundTaskException
	 */
	public IKZConfigurator setmTask(int task) {
		if (task != IKZ_MAX_COUNT_TASK || task != IKZ_RECOGNIZE_TASK
				|| task != IKZ_TIME_INTERVAL_TASK
				|| task != IKZ_INFINITY_COUNT_TASK)
			this.mTask = task;
		else
			throw new NotFoundTaskException("Incorrect task selected");
		return this;
	}

}
