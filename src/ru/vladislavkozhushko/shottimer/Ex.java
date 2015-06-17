package ru.vladislavkozhushko.shottimer;

public class Ex {
	
	private int mImageId;
	private String mTitle;
	private String mDescriptin;
	private int mMaxCount=-1;
	private int mTimeLimit=-1;
	public Ex(int img, String title, String desc, int count, int timelimit_ms) {
		mImageId=img;
		mTitle=title;
		mDescriptin=desc;
		mMaxCount=count;
		mTimeLimit=timelimit_ms;
	}
	public int getImageId() {
		return mImageId;
	}
	public String getTitle() {
		return mTitle;
	}
	public String getDescriptin() {
		return mDescriptin;
	}
	public int MaxCount() {
		return mMaxCount;
	}
	public int TimeLimit() {
		return mTimeLimit;
	}
	public void setImageId(int mImageId) {
		this.mImageId = mImageId;
	}
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public void setmDescriptin(String mDescriptin) {
		this.mDescriptin = mDescriptin;
	}
	public void setMaxCount(int mHaveMaxCount) {
		this.mMaxCount = mHaveMaxCount;
	}
	public void setTimeLimit(int mHaveTimeLimit) {
		this.mTimeLimit = mHaveTimeLimit;
	}

}
