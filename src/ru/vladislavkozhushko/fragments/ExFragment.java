package ru.vladislavkozhushko.fragments;

import java.util.ArrayList;
import java.util.List;

import ru.vladislavkozhushko.shottimer.EditEx;
import ru.vladislavkozhushko.shottimer.Ex;
import ru.vladislavkozhushko.shottimer.ExAdapter;
import ru.vladislavkozhushko.shottimer.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ExFragment extends Fragment  {

	private ListView mExListView;
	private ExAdapter mExAdapter;
	private List<Ex> mExList;

	public ExFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.fragment_ex, container, false);
		mExList = new ArrayList<>(5);
		mExList.add(new Ex(R.drawable.ic_launcher, "Первый",
				"Совершить выстрел за 2 секунды", true, true));
		mExList.add(new Ex(R.drawable.ic_launcher, "Десяточка",
				"Совершить 10 выстрелов без ограничения по времени", true, false));
		mExList.add(new Ex(R.drawable.ic_launcher, "7 секунд",
				"Совершить максимальное количество прицельных выстрелов за 7 секунд", false, true));
		mExAdapter=new ExAdapter(getActivity(), mExList);
		mExListView=(ListView) rootView.findViewById(R.id.ExListView);
		mExListView.setAdapter(mExAdapter);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_ex_menu, menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.menuAdd:
			startActivity(new Intent(getActivity(),EditEx.class));
			return true;
		case R.id.menuInfo:
			
			return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
}
