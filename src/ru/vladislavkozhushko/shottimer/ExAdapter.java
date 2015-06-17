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
	private int max_count, timelim;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.ex_item, parent,
					false);
			holder.img = (ImageView) convertView.findViewById(R.id.imageEx);
			holder.title = (TextView) convertView
					.findViewById(R.id.textExTitle);
			holder.desc = (TextView) convertView
					.findViewById(R.id.textExDescription);
			holder.maxcount=(TextView) convertView.findViewById(R.id.textCountLimitIt);
			holder.timelim=(TextView) convertView.findViewById(R.id.textTimeLimitIt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Ex ex = mExList.get(position);
		holder.img.setImageResource(ex.getImageId());
		max_count=ex.MaxCount();
		if (max_count>0){
			holder.maxcount.setText(String.valueOf(max_count));
			holder.maxcount.setVisibility(View.VISIBLE);
		}
		else {
			holder.maxcount.setVisibility(View.GONE);
		}
		timelim=ex.TimeLimit();
		if (timelim>0){
			holder.timelim.setText(String.valueOf(timelim/100));
			holder.timelim.append(".");
			holder.timelim.append(String.valueOf(timelim%100));
			holder.timelim.setVisibility(View.VISIBLE);
		}			
		else{
			holder.timelim.setVisibility(View.GONE);
		}
		holder.title.setText(ex.getTitle());
		holder.desc.setText(ex.getDescriptin());
		return convertView;
	}

	private class ViewHolder {
		ImageView img;
		TextView title, desc,maxcount,timelim;
	}

}
