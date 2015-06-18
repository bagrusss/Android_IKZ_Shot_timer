package ru.vladislavkozhushko.fragments;

import java.util.ArrayList;
import java.util.List;

import ru.vladislavkozhushko.shottimer.EditEx;
import ru.vladislavkozhushko.shottimer.Ex;
import ru.vladislavkozhushko.shottimer.ExAdapter;
import ru.vladislavkozhushko.shottimer.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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

public class ExFragment extends Fragment   {

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
				"Совершить выстрел за 2 секунды", 1, 200));
		mExList.add(new Ex(R.drawable.ic_launcher, "Десяточка",
				"Совершить 10 выстрелов без ограничения по времени", 10, -1));
		mExList.add(new Ex(R.drawable.ic_launcher, "7 секунд",
				"Совершить максимальное количество прицельных выстрелов за 7 секунд", 0, 700));
		mExList.add(new Ex(R.drawable.ic_launcher, "Бесконеч.",
				"Стрельба без ограничений по времени и количеству выстрелов", 0, 0));
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
			showInfoDialog();
			return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	void showInfoDialog(){		
		View v = getActivity().getLayoutInflater().inflate(R.layout.ex_info_dialog, null);
		AlertDialog.Builder builder=new Builder(getActivity()).setTitle(R.string.text_info).setView(v).setIcon(R.drawable.ic_info);
		builder.create().show();				
	}
	
}
