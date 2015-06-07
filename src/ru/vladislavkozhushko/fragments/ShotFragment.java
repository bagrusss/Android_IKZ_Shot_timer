package ru.vladislavkozhushko.fragments;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.time.StopWatch;

import ru.vladislavkozhushko.shottimer.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ShotFragment extends Fragment implements OnClickListener {

	private Button mWorkButton, mResetButton;
	private TextView mStopWatchText;
	private Spinner mSpinner;

	private byte state = 0;
	private Timer mTimer;
	private StopWatch mStopWatch;
	private Handler mHandler;

	public ShotFragment() {

	}

	private void Reset() {
		mStopWatch.reset();
		state = 0;
		mResetButton.setEnabled(false);
		mStopWatchText.setText(getActivity().getString(R.string.def_time_val));
	}

	private void Stop() {
		mStopWatch.suspend();
		mTimer.cancel();
		state = 1;
		mWorkButton.setBackgroundDrawable(getActivity().getResources()
				.getDrawable(R.drawable.green_btn_selector));
		mWorkButton.setText(getActivity().getString(R.string.text_start));
		mSpinner.setEnabled(true);
		mResetButton.setEnabled(true);

	}

	private void Start() {
		if (mStopWatch.isStarted())
			mStopWatch.resume();
		else
			mStopWatch.start();
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);
			}
		}, 0, 10);
		state = 2;
		mSpinner.setEnabled(false);
		mWorkButton.setBackgroundDrawable(getActivity().getResources()
				.getDrawable(R.drawable.red_btn_selector));
		mWorkButton.setText(getActivity().getString(R.string.text_stop));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mStopWatch = new StopWatch();
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
		mWorkButton.setOnClickListener(this);
		mResetButton = (Button) rootView.findViewById(R.id.resetButton);
		mResetButton.setOnClickListener(this);
		mSpinner = (Spinner) rootView.findViewById(R.id.EX_spinner);
		return rootView;
	}

	@Override
	public void onDestroy() {
		mStopWatch = null;
		super.onDestroy();
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
		}
	}
}
