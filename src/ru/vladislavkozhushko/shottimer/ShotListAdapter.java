package ru.vladislavkozhushko.shottimer;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShotListAdapter extends BaseAdapter {

	private List<Shot> mShots;
	private LayoutInflater mInflater;
	private Context mContext;

	public List<Shot> getShotList() {
		return mShots;
	}

	public ShotListAdapter(Context cont, List<Shot> list) {
		mContext = cont;
		mShots=list;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mShots.size();
	}

	@Override
	public Object getItem(int position) {
		return mShots.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.shot_item, parent, false);
			holder = new ViewHolder();
			holder.mTextNum = ((TextView) convertView
					.findViewById(R.id.TextNumber));
			holder.mTextSplit = ((TextView) convertView
					.findViewById(R.id.TextShotTime));
			holder.mTextTime = ((TextView) convertView
					.findViewById(R.id.TextTime));
			/*holder.mTextNum.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
					"digital.ttf"));
			holder.mTextSplit.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
					"digital.ttf"));
			holder.mTextTime.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
					"digital.ttf"));*/
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Shot shot = mShots.get(position);
		holder.mTextNum.setText(String.valueOf(shot.getmNumber()));
		holder.mTextSplit.setText(shot.getmSplit());
		holder.mTextTime.setText(shot.getmTime());
		return convertView;
	}

	private class ViewHolder {
		TextView mTextNum;
		TextView mTextSplit;
		TextView mTextTime;
	}

}
