package ru.vladislavkozhushko.shottimer;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExAdapter extends BaseAdapter {

	private List<Ex> mExList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public ExAdapter(Context cont, List<Ex> l) {
		mContext = cont;
		mExList = l;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mExList.size();
	}

	@Override
	public Object getItem(int position) {
		return mExList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.ex_item, parent,
					false);
			holder.img = (ImageView) convertView.findViewById(R.id.imageEx);
			holder.hasCount = (ImageView) convertView
					.findViewById(R.id.imageCountLimit);
			holder.hasTimeLimit = (ImageView) convertView
					.findViewById(R.id.imageTimeLimit);
			holder.title = (TextView) convertView
					.findViewById(R.id.textExTitle);
			holder.desc = (TextView) convertView
					.findViewById(R.id.textExDescription);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Ex ex = mExList.get(position);
		holder.img.setImageResource(ex.getmImageId());
		if (ex.ismHaveMaxCount())
			holder.hasCount.setImageResource(R.drawable.ic_warn);
		else
			holder.hasCount.setImageDrawable(null);
		if (ex.ismHaveTimeLimit())
			holder.hasTimeLimit.setImageResource(R.drawable.ic_timelimit);
		else
			holder.hasTimeLimit.setImageDrawable(null);
		holder.title.setText(ex.getmTitle());
		holder.desc.setText(ex.getmDescriptin());
		return convertView;
	}

	private class ViewHolder {
		ImageView img, hasCount, hasTimeLimit;
		TextView title, desc;

	}

}
