package ru.vladislavkozhushko.shottimer;

public class Ex {
	
	private int mImageId;
	private String mTitle;
	private String mDescriptin;
	private boolean mHaveMaxCount;
	private boolean mHaveTimeLimit;
	public Ex(int img, String title, String desc, boolean hasmc, boolean hastl) {
		mImageId=img;
		mTitle=title;
		mDescriptin=desc;
		mHaveMaxCount=hasmc;
		mHaveTimeLimit=hastl;
	}
	public int getmImageId() {
		return mImageId;
	}
	public String getmTitle() {
		return mTitle;
	}
	public String getmDescriptin() {
		return mDescriptin;
	}
	public boolean ismHaveMaxCount() {
		return mHaveMaxCount;
	}
	public boolean ismHaveTimeLimit() {
		return mHaveTimeLimit;
	}
	public void setmImageId(int mImageId) {
		this.mImageId = mImageId;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public void setmDescriptin(String mDescriptin) {
		this.mDescriptin = mDescriptin;
	}
	public void setmHaveMaxCount(boolean mHaveMaxCount) {
		this.mHaveMaxCount = mHaveMaxCount;
	}
	public void setmHaveTimeLimit(boolean mHaveTimeLimit) {
		this.mHaveTimeLimit = mHaveTimeLimit;
	}

}
