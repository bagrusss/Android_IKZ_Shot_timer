package ru.vladislavkozhushko.fragments;


import ru.vladislavkozhushko.shottimer.R;
import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {
	

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.settings);	
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getListView().setPadding(0, 0, 0, 0);
	}
	
	public SettingsFragment() {
		
	}

}
