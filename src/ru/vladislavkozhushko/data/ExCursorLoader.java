package ru.vladislavkozhushko.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

public class ExCursorLoader extends CursorLoader {
	
	private Context mContext;
	public ExCursorLoader(Context context) {
		super(context);
		mContext=context;
	}

	@Override
	public Cursor loadInBackground() {		
		return ExSQLiteOpenHelper.getInstance(mContext).getAllEx();
	}

}
