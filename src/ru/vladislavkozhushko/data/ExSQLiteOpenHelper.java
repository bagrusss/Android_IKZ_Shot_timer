package ru.vladislavkozhushko.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExSQLiteOpenHelper extends SQLiteOpenHelper {	
	
	public static ExSQLiteOpenHelper mHelper;
	private final String DB_TAG="ExSQLiteOpenHelper";
	private static final String mDBName="ShotTimerDB.db";
	private final static int mDBVer=1;
	public static final String TABLE_EX="TABLE_EX",
								ID="_id",
								EX_TITLE="title",
								EX_DESCRIPTION="description",
								EX_SHOTS_COUNT="count",
								EX_TIMELIMIT_MS="timelimitMS",
								EX_SHOT_ACTIVATION="shotactivation";
	//далее можно будет добавить пользователей и результаты
	public static ExSQLiteOpenHelper getInstance(Context cont){
		if (mHelper==null){
			mHelper=new ExSQLiteOpenHelper(cont, mDBName, null, mDBVer);
		}
		return mHelper;
	}
	
	private ExSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	private ExSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder query=new StringBuilder();
		query.append("CREATE TABLE").append(" IF NOT EXISTS ").append(TABLE_EX).append("(")
		.append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE DEFAULT 1, ")
		.append(EX_TITLE).append(" TEXT NOT NULL, ").append(EX_DESCRIPTION).append(" TEXT, ")
		.append(EX_TIMELIMIT_MS).append(" INTEGER, ").append(EX_SHOTS_COUNT).append(" INTEGER, ")
		.append(EX_SHOT_ACTIVATION).append(" BIT);");
		db.execSQL(query.toString());
		List<ContentValues> vals=new ArrayList<ContentValues>(4);
		ContentValues cv=new ContentValues();
		cv.put(EX_TITLE, "Бесконеч.");
		cv.put(EX_DESCRIPTION, "Стрельба без ограничений по времени и количеству выстрелов");
		cv.put(EX_TIMELIMIT_MS, 0);
		cv.put(EX_SHOTS_COUNT, 0);
		cv.put(EX_SHOT_ACTIVATION, false);
		vals.add(cv);
		cv=new ContentValues();
		cv.put(EX_TITLE, "Первый");
		cv.put(EX_DESCRIPTION, "Совершить выстрел за 2 секунды");
		cv.put(EX_TIMELIMIT_MS, 2000);
		cv.put(EX_SHOTS_COUNT, 1);
		cv.put(EX_SHOT_ACTIVATION, false);
		vals.add(cv);
		cv=new ContentValues();
		cv.put(EX_TITLE, "Десяточка");
		cv.put(EX_DESCRIPTION, "Совершить 10 выстрелов без ограничения по времени");
		cv.put(EX_TIMELIMIT_MS, 0);
		cv.put(EX_SHOTS_COUNT, 10);
		cv.put(EX_SHOT_ACTIVATION, false);
		vals.add(cv);
		cv=new ContentValues();
		cv.put(EX_TITLE, "7 секунд");
		cv.put(EX_DESCRIPTION, "Совершить максимальное количество прицельных выстрелов за 7 секунд");
		cv.put(EX_TIMELIMIT_MS, 7000);
		cv.put(EX_SHOTS_COUNT, 7);
		cv.put(EX_SHOT_ACTIVATION, false);
		vals.add(cv);
		try{
			db.beginTransaction();
			for (ContentValues values: vals){
				db.insert(TABLE_EX, null, values);
			}
			db.setTransactionSuccessful();
		} finally{
			db.endTransaction();
		}
		Log.d(DB_TAG,"DB Created!");
	}
	
	public Cursor getAllEx(){
		SQLiteDatabase db=getReadableDatabase();		
		return db.query(TABLE_EX, null, null, null, null, null, null);	
	}
	
	public synchronized long insertEX(String title, String description, long timelimitMS, int maxcount, boolean shotactivation){
		ContentValues cv=new ContentValues();
		cv.put(EX_TITLE, title);
		cv.put(EX_DESCRIPTION, description);
		cv.put(EX_TIMELIMIT_MS, timelimitMS>0?timelimitMS:0);
		cv.put(EX_SHOTS_COUNT, maxcount>0?maxcount:0);
		cv.put(EX_SHOT_ACTIVATION, shotactivation);
		SQLiteDatabase db=getWritableDatabase();
		long res=0L;
		try{
			db.beginTransaction();
			Log.i(DB_TAG, String.valueOf(res=db.insert(TABLE_EX, null, cv)));
			db.setTransactionSuccessful();
		} finally{
			db.endTransaction();
		}
		return res;
	}
	public synchronized int updateEX(long id, String title, String description, long timelimitMS, int maxcount, int shotactivation){
		ContentValues cv=new ContentValues();
		cv.put(EX_TITLE, title);
		cv.put(EX_DESCRIPTION, description);
		cv.put(EX_TIMELIMIT_MS, timelimitMS>0?timelimitMS:0);
		cv.put(EX_SHOTS_COUNT, maxcount>0?maxcount:0);
		if (shotactivation<2)
			cv.put(EX_SHOT_ACTIVATION, shotactivation);
		SQLiteDatabase db=getWritableDatabase();
		int res=0;
		try{
			db.beginTransaction();
			Log.i(DB_TAG, String.valueOf(res=db.update(TABLE_EX, cv, "_ID=?", new String[]{String.valueOf(id)})));
			db.setTransactionSuccessful();
		} finally{
			db.endTransaction();
		}
		return res;
	}
	
	public synchronized int updateFulfilmentEx(long id, boolean state){
		ContentValues cv=new ContentValues();
		cv.put(EX_SHOT_ACTIVATION, state);
		SQLiteDatabase db=getWritableDatabase();
		int res=0;
		try{
			db.beginTransaction();
			Log.i(DB_TAG, String.valueOf(res=db.update(TABLE_EX, cv, "_ID=?", new String[]{String.valueOf(id)})));
			db.setTransactionSuccessful();
		} finally{
			db.endTransaction();
		}
		return res;
	}
	
	public synchronized int deleteExByID(long id){
		int res=0;
		SQLiteDatabase db=getWritableDatabase();
		try{
			db.beginTransaction();
			Log.i(DB_TAG, String.valueOf(res=db.delete(TABLE_EX, "_ID=?", new String[]{String.valueOf(id)})));
			db.setTransactionSuccessful();
		} finally{
			db.endTransaction();
		}
		return res;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		

	}
}
