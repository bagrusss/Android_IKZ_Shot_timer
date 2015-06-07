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
	
	public SettingsFragment() {
		// TODO Auto-generated constructor stub
	}

}
