package ru.vladislavkozhushko.fragments;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.time.StopWatch;

import ru.vladislavkozhushko.data.ExCursorLoader;
import ru.vladislavkozhushko.data.ExSQLiteOpenHelper;
import ru.vladislavkozhushko.shottimer.R;
import ru.vladislavkozhushko.shottimer.Shot;
import ru.vladislavkozhushko.shottimer.ShotListAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressWarnings("deprecation")
public class ShotFragment extends Fragment implements OnClickListener,
		LoaderCallbacks<Cursor>, OnItemSelectedListener {

	private Button mWorkButton, mResetButton;
	private ImageButton mInfoButton;
	private TextView mStopWatchText;
	private Spinner mSpinner;
	private ListView mListView;
	private ShotListAdapter mShotListAdapter;
	private CursorAdapter mSpinnerAdapter;
	private List<Shot> mShots;
	private MediaPlayer mMediaPlayer;
	private Dialog mDialog;
	private Context mContext;
	private boolean isStopWatchStarted = false; // костыль для Xiaomi

	private byte state = 0;
	private Timer mTimer;
	private StopWatch mStopWatch;
	private Handler mHandler;

	public ShotFragment() {

	}

	private void Reset() {
		mShots.clear();
		mShotListAdapter.notifyDataSetChanged();
		mStopWatch.reset();
		isStopWatchStarted = false;
		state = 0;
		mResetButton.setEnabled(false);
		mStopWatchText.setText(mContext.getString(R.string.def_time_val));
	}

	private void Stop() {
		mStopWatch.suspend();
		mTimer.cancel();
		mTimer = null;
		state = 1;
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			mWorkButton.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.green_btn_selector));
		} else {
			mWorkButton.setBackground(mContext.getResources().getDrawable(
					R.drawable.green_btn_selector));
		}
		mWorkButton.setText(mContext.getString(R.string.text_start));
		mSpinner.setEnabled(true);
		mResetButton.setEnabled(true);
	}

	private void Start() {
		if (isStopWatchStarted) // mStopWatch.isStarted() но с Xiaomi метод не
								// существует?
			mStopWatch.resume();
		else {
			mWorkButton.setEnabled(false);
			preparePlayer();
			mMediaPlayer.start();
		}
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);
			}
		}, 0, 10);
		state = 2;
		mSpinner.setEnabled(false);
		mResetButton.setEnabled(false);
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			mWorkButton.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.red_btn_selector));
		} else {
			mWorkButton.setBackground(mContext.getResources().getDrawable(
					R.drawable.red_btn_selector));
		}
		mWorkButton.setText(mContext.getString(R.string.text_stop));
	}

	class SpinnerHolder {
		String description;
		long timelim;
		int maxCount;
		TextView title;
		boolean isSuccessful;
	}

	@SuppressLint("HandlerLeak")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mStopWatch = new StopWatch();
		mContext = getActivity();
		mTimer = new Timer();
		mShots = new LinkedList<Shot>();
		mShotListAdapter = new ShotListAdapter(mContext, mShots);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					mStopWatchText.setText(mStopWatch.toString().substring(3,
							11));
				}
			}
		};
		View rootView = inflater.inflate(R.layout.fragment_shot, container,
				false);
		mStopWatchText = (TextView) rootView.findViewById(R.id.stopwatch);
		mStopWatchText.setTypeface(Typeface.createFromAsset(
				mContext.getAssets(), "digital.ttf"));
		mInfoButton = (ImageButton) rootView.findViewById(R.id.infoButton);
		mInfoButton.setOnClickListener(this);
		mWorkButton = (Button) rootView.findViewById(R.id.workButton);
		((TextView) rootView.findViewById(R.id.TextNumber))
				.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
						"digital.ttf"));
		((TextView) rootView.findViewById(R.id.TextShotTime))
				.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
						"digital.ttf"));
		((TextView) rootView.findViewById(R.id.TextTime)).setTypeface(Typeface
				.createFromAsset(mContext.getAssets(), "digital.ttf"));
		mListView = (ListView) rootView.findViewById(R.id.shotsList);
		mListView.setAdapter(mShotListAdapter);
		mWorkButton.setOnClickListener(this);
		mResetButton = (Button) rootView.findViewById(R.id.resetButton);
		mResetButton.setOnClickListener(this);
		mSpinner = (Spinner) rootView.findViewById(R.id.EX_spinner);
		mSpinnerAdapter = new CursorAdapter(mContext, null) {
			
			@Override
			public View newView(Context context, Cursor c, ViewGroup parent) {
				View v = LayoutInflater.from(context).inflate(
						android.R.layout.simple_list_item_1, parent, false);
				SpinnerHolder holder = new SpinnerHolder();
				holder.title = (TextView) v.findViewById(android.R.id.text1);
				v.setTag(holder);
				return v;
			}

			@Override
			public void bindView(View v, Context context, Cursor c) {
				SpinnerHolder holder = (SpinnerHolder) v.getTag();
				holder.title.setText(c.getString(c
						.getColumnIndex(ExSQLiteOpenHelper.EX_TITLE)));
				holder.description = c.getString(c
						.getColumnIndex(ExSQLiteOpenHelper.EX_DESCRIPTION));
				holder.maxCount = c.getInt(c
						.getColumnIndex(ExSQLiteOpenHelper.EX_SHOTS_COUNT));
				holder.timelim = c.getLong(c
						.getColumnIndex(ExSQLiteOpenHelper.EX_TIMELIMIT_MS));
				holder.isSuccessful = c.getInt(c
						.getColumnIndex(ExSQLiteOpenHelper.EX_SHOT_ACTIVATION)) > 0;
			}
		};
		mSpinner.setAdapter(mSpinnerAdapter);
		mSpinner.setOnItemSelectedListener(this);
		getLoaderManager().initLoader(0, null, this);
		return rootView;
	}

	void preparePlayer() {
		mMediaPlayer = MediaPlayer.create(mContext, R.raw.ex);
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				// mp.prepareAsync();
				mStopWatch.start();
				isStopWatchStarted = true;
				mWorkButton.setEnabled(true);
				mp.reset();
				mp.release();
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mTimer = null;
		mShots = null;
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		// mStopWatch = null;
	}
	
	/*
	@Override
	public void onPause() {
		// mMediaPlayer.release();
		super.onPause();
	}*/

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.workButton:
			if (state <= 1)
				Start();
			else
				Stop();
			return;
		case R.id.resetButton:
			Reset();
			return;
		case R.id.infoButton:
			ShotInfoAboutEx();
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new ExCursorLoader(mContext);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> l, Cursor c) {
		mSpinnerAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> l) {

	}

	private StringBuilder mExTitle, mExDescription;
	private int mMaxCount;
	private long mTimeLim, mID;
	private boolean isSuccessful;
	private TextView mDesc, mTextCountLimit, mTextTimeLimit;

	void ShotInfoAboutEx() {
		View v;
		if (mDialog == null) {
			v = LayoutInflater.from(mContext).inflate(R.layout.ex_info_dialog,
					null);
			mDesc = (TextView) v.findViewById(R.id.textDescriptionInfo);
			mTextCountLimit = (TextView) v.findViewById(R.id.textViewCountInfo);
			mTextTimeLimit = (TextView) v
					.findViewById(R.id.textViewTimeLiminInfo);
			AlertDialog.Builder builder = new Builder(mContext)
					.setIcon(R.drawable.ic_info)
					.setTitle(mExTitle.toString())
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).setView(v);
			mDialog = builder.create();
		}
		mDesc.setText(mExDescription.toString());
		StringBuilder str = new StringBuilder(
				getString(R.string.text_shotsCount));
		str.append(": ");
		mTextCountLimit.setText(str.toString());
		mTextCountLimit.append(mMaxCount > 0 ? String.valueOf(mMaxCount)
				: getString(R.string.text_no));
		str.setLength(0);
		str.append(getString(R.string.text_timelimit)).append(": ");
		mTextTimeLimit.setText(str.toString());
		mTextTimeLimit.append(mTimeLim > 0 ? String.format(Locale.US, "%.2f",
				mTimeLim / 1000.0) : getString(R.string.text_no));
		mDialog.setTitle(mExTitle);
		mDialog.show();
	}

	void showExStatusDialog(boolean isSuccess) {
		View v = LayoutInflater.from(mContext).inflate(
				R.layout.ex_result_dialog, null);
		TextView textMessage = (TextView) v.findViewById(R.id.textMessage);
		ImageView iv = (ImageView) v.findViewById(R.id.imageStatus);
		if (isSuccessful) {
			textMessage.setText(R.string.text_success);
			iv.setImageResource(R.drawable.ic_galka);
		} else {
			textMessage.setText(R.string.text_fail);
			iv.setImageResource(R.drawable.ic_galka);
		}
		// обновить статус упражнения, если оно не выполнялось ранее
		if (!isSuccessful && isSuccess) {
			ExSQLiteOpenHelper dbhelper = ExSQLiteOpenHelper
					.getInstance(mContext);
			dbhelper.updateFulfilmentEx(mID, true);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		SpinnerHolder holder = (SpinnerHolder) v.getTag();
		if (mExTitle == null) {
			mExTitle = new StringBuilder(holder.title.getText());
			mExDescription = new StringBuilder(holder.description);
		} else {
			mExTitle.setLength(0);
			mExTitle.append(holder.title.getText());
			mExDescription.setLength(0);
			mExDescription.append(holder.description);
		}
		mMaxCount = holder.maxCount;
		mTimeLim = holder.timelim;
		isSuccessful = holder.isSuccessful;
		mID = id;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
