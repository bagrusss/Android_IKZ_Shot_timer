package ru.vladislavkozhushko.fragments;

import ru.vladislavkozhushko.shottimer.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShotFragment extends Fragment {

	public ShotFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shot, container,
				false);
		TextView sw = (TextView) rootView.findViewById(R.id.stopwatch);
		sw.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
				"digital.ttf"));
		TextView tv = (TextView) rootView.findViewById(R.id.textTime);
		tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
				"digital.ttf"));
		return rootView;
	}

}
