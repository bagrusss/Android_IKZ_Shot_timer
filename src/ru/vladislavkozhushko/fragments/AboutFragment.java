package ru.vladislavkozhushko.fragments;

import ru.vladislavkozhushko.shottimer.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment{

	public AboutFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_about, container, false);
		TextView tv=(TextView)rootView.findViewById(R.id.aboutDev);
		tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "digital.ttf"));
		return rootView;
	}
	
}
