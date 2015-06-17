package ru.vladislavkozhushko.fragments;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.time.StopWatch;

import ru.vladislavkozhushko.shottimer.R;
import ru.vladislavkozhushko.shottimer.Shot;
import ru.vladislavkozhushko.shottimer.ShotListAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressWarnings("deprecation")
public class ShotFragment extends Fragment implements OnClickListener {

	private Button mWorkButton, mResetButton;
	private TextView mStopWatchText;
	private Spinner mSpinner;
	private ListView mListView;
	private ShotListAdapter mShotListAdapter;
	private List<Shot> mShots;
	private MediaPlayer mMediaPlayer;

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
		state = 0;
		mResetButton.setEnabled(false);
		mStopWatchText.setText(getActivity().getString(R.string.def_time_val));
	}

	private void Stop() {
		mStopWatch.suspend();
		mTimer.cancel();
		mTimer = null;
		state = 1;
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			mWorkButton.setBackgroundDrawable(getActivity().getResources()
					.getDrawable(R.drawable.green_btn_selector));
		} else {
			mWorkButton.setBackground(getActivity().getResources().getDrawable(
					R.drawable.green_btn_selector));
		}
		mWorkButton.setText(getActivity().getString(R.string.text_start));
		mSpinner.setEnabled(true);
		mResetButton.setEnabled(true);
	}

	private void Start() {		
		if (mStopWatch.isStarted())
			mStopWatch.resume();
		else {
			mWorkButton.setEnabled(false);
			mMediaPlayer.start();
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mStopWatch.start();
					mWorkButton.setEnabled(true);
				}
			});
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
			mWorkButton.setBackgroundDrawable(getActivity().getResources()
					.getDrawable(R.drawable.red_btn_selector));
		} else {
			mWorkButton.setBackground(getActivity().getResources().getDrawable(
					R.drawable.red_btn_selector));
		}
		mWorkButton.setText(getActivity().getString(R.string.text_stop));
	}

	@SuppressLint("HandlerLeak")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mStopWatch = new StopWatch();
		mTimer = new Timer();
		mShots = new LinkedList<Shot>();
		mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ex);
		mShots.add(new Shot(1, "00.00", "01.03"));
		mShots.add(new Shot(2, "01.00", "02.03"));
		mShots.add(new Shot(3, "00.56", "02.59"));
		mShotListAdapter = new ShotListAdapter(getActivity(), mShots);
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
		mStopWatchText.setTypeface(Typeface.createFromAsset(getActivity()
				.getAssets(), "digital.ttf"));
		mWorkButton = (Button) rootView.findViewById(R.id.workButton);
		((TextView) rootView.findViewById(R.id.TextNumber))
				.setTypeface(Typeface.createFromAsset(
						getActivity().getAssets(), "digital.ttf"));
		((TextView) rootView.findViewById(R.id.TextShotTime))
				.setTypeface(Typeface.createFromAsset(
						getActivity().getAssets(), "digital.ttf"));
		((TextView) rootView.findViewById(R.id.TextTime)).setTypeface(Typeface
				.createFromAsset(getActivity().getAssets(), "digital.ttf"));
		mListView = (ListView) rootView.findViewById(R.id.shotsList);
		mListView.setAdapter(mShotListAdapter);
		mWorkButton.setOnClickListener(this);
		mResetButton = (Button) rootView.findViewById(R.id.resetButton);
		mResetButton.setOnClickListener(this);
		mSpinner = (Spinner) rootView.findViewById(R.id.EX_spinner);
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mTimer = null;
		mShots = null;
		mMediaPlayer=null;
		// mStopWatch = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.workButton:
			if (state <= 1)
				Start();
			else
				Stop();
			break;
		case R.id.resetButton:
			Reset();
			break;
		case R.id.infoButton:

		}
	}
}
