package ru.vladislavkozhushko.fragments;

import java.util.ArrayList;
import java.util.List;

import ru.vladislavkozhushko.shottimer.Ex;
import ru.vladislavkozhushko.shottimer.ExAdapter;
import ru.vladislavkozhushko.shottimer.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ExFragment extends Fragment {

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
		mExList.add(new Ex(R.drawable.ic_launcher, "������",
				"��������� ��������� ������� �� 2 �������", true, true));
		mExList.add(new Ex(R.drawable.ic_launcher, "���������",
				"��������� ��������� 10 ��������� ��� ����������� �� �������", true, false));
		mExList.add(new Ex(R.drawable.ic_launcher, "7 ������",
				"��������� ��������� ������������ ���������� ���������� ��������� �� 7 ������", false, true));
		mExAdapter=new ExAdapter(getActivity(), mExList);
		mExListView=(ListView) rootView.findViewById(R.id.ExListView);
		mExListView.setAdapter(mExAdapter);
		return rootView;
	}
}
