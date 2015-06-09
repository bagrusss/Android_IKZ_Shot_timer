package ru.vladislavkozhushko.shottimer;

public class Shot {
	
	private String mTime;
	private String mSplit;
	private int mNumber;
	
	public Shot(int num, String split, String time) {
		mNumber=num;
		mTime=time;
		mSplit=split;
	}

	public String getmTime() {
		return mTime;
	}

	public String getmSplit() {
		return mSplit;
	}

	public int getmNumber() {
		return mNumber;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public void setmSplit(String mSplit) {
		this.mSplit = mSplit;
	}

	public void setmNumber(int mNumber) {
		this.mNumber = mNumber;
	}

}
