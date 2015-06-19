package ru.kozhushkovladislav.ikz_recognize_lib;

import ru.kozhushkovladislav.exeptions.IncorrectConfigurationsException;

// класс синглтон
public class IKZRecognizer {

	// статус распознавателя

	public static final int STATUS_RECORDING = 1;
	public static final int STATUS_STOPPED = 0;
	public static final int STATUS_PAUSED = 2;
	public static final int STATUS_READY = 3;
	public static final int STATUS_NOT_INITIALIZED = 4;

	private int mStatus = STATUS_NOT_INITIALIZED;
	private IKZConfigurator mConfigs;

	private static IKZRecognizer mIKZRecognizer;

	private IKZRecognizer() {

	}

	public static IKZRecognizer getInstance() {
		if (mIKZRecognizer == null)
			mIKZRecognizer = new IKZRecognizer();
		return mIKZRecognizer;
	}

	public IKZConfigurator getConfigs() {
		return mConfigs;
	}

	public IKZRecognizer setConfigs(IKZConfigurator configs) {
		this.mConfigs = configs;
		parseConfigs();
		return this;
	}

	private void parseConfigs() {

	}

	/**
	 * Start sounds recognizing if configurations are correct
	 */
	public void start() {
		if (mStatus != STATUS_NOT_INITIALIZED)
			throw new IncorrectConfigurationsException(
					"Recognizer not initialized");

		mStatus = STATUS_RECORDING;
	}

	public void pause() {

		mStatus = STATUS_PAUSED;
	}

	public void stop() {

		mStatus = STATUS_STOPPED;
	}

	public int status() {
		return mStatus;
	}

}
