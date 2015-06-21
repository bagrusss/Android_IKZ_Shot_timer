package ru.vladislavkozhushko.fragments;

import java.util.Locale;

import ru.vladislavkozhushko.data.ExCursorLoader;
import ru.vladislavkozhushko.data.ExSQLiteOpenHelper;
import ru.vladislavkozhushko.shottimer.EditEx;
import ru.vladislavkozhushko.shottimer.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ExFragment extends Fragment implements LoaderCallbacks<Cursor>, OnItemClickListener   {

	private ListView mExListView;
	private CursorAdapter mCursorAdapter;
	public static final String ACTIVITY_TITLE="ActTit",
			TITLE="Tit",
			DESCRIPTION="Decr",
			MAX_COUNT="MaxCount",
			TIME_LIM="TimeLim",
			MODE_EDIT="mode_ed",
			ID="id";
	public ExFragment() {

	}
	// для курсор_адаптера
	class ExHolder{
		ImageView img; //если выполнено, будет галка
		TextView title, desc, maxcount, timelim;
		int id;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.fragment_ex, container, false);
		mCursorAdapter=new CursorAdapter(getActivity(), null) {
			
			@Override
			public View newView(Context context, Cursor cursor, ViewGroup parent) {
				View v=LayoutInflater.from(context).inflate(R.layout.ex_item, parent, false);
				ExHolder holder=new ExHolder();
				holder.img = (ImageView) v.findViewById(R.id.imageEx);
				holder.title = (TextView) v.findViewById(R.id.textExTitle);
				holder.desc = (TextView) v.findViewById(R.id.textExDescription);
				holder.maxcount=(TextView) v.findViewById(R.id.textCountLimitIt);
				holder.timelim=(TextView) v.findViewById(R.id.textTimeLimitIt);
				v.setTag(holder);
				return v;
			}
			
			int shotscount, id;
			long timelim;
			@Override
			public void bindView(View view, Context context, Cursor c) {
				ExHolder holder=(ExHolder) view.getTag();
				id = c.getInt(c.getColumnIndex(ExSQLiteOpenHelper.ID));
				holder.id=id;
				holder.title.setText(c.getString(c.getColumnIndex(ExSQLiteOpenHelper.EX_TITLE)));
				holder.desc.setText(c.getString(c.getColumnIndex(ExSQLiteOpenHelper.EX_DESCRIPTION)));
				shotscount=c.getInt(c.getColumnIndex(ExSQLiteOpenHelper.EX_SHOTS_COUNT));
				if (shotscount>0){
					holder.maxcount.setText(String.valueOf(shotscount));
					holder.maxcount.setVisibility(View.VISIBLE);
				} else holder.maxcount.setVisibility(View.GONE);
				holder.maxcount.setTag(shotscount);
				timelim=c.getLong(c.getColumnIndex(ExSQLiteOpenHelper.EX_TIMELIMIT_MS));
				if (timelim>0){
					holder.timelim.setText(String.format(Locale.US,"%.2f", timelim/1000.0));
					holder.timelim.setVisibility(View.VISIBLE);
				} else holder.timelim.setVisibility(View.GONE);
				holder.timelim.setTag(timelim);
				if (c.getInt(c.getColumnIndex(ExSQLiteOpenHelper.EX_SHOT_ACTIVATION))>0){
					holder.img.setImageResource(R.drawable.ic_galka);
				} else holder.img.setImageResource(R.drawable.ic_launcher);
			}

		};
		mExListView=(ListView) rootView.findViewById(R.id.ExListView);
		mExListView.setAdapter(mCursorAdapter);
		mExListView.setOnItemClickListener(this);
		getLoaderManager().initLoader(0, null, this);
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
			Intent intent =new Intent(getActivity(),EditEx.class);
			intent.putExtra("mode_edit", false);
			startEditExActivity(intent);
			return true;
		case R.id.menuInfo:
			showInfoDialog();
			return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	
	
	void showInfoDialog(){		
		View v = getActivity().getLayoutInflater().inflate(R.layout.ex_info_dialog, null);
		AlertDialog.Builder builder=new Builder(getActivity()).setTitle(R.string.text_info).setView(v).setIcon(R.drawable.ic_info);
		builder.create().show();				
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new ExCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {		
		mCursorAdapter.swapCursor(c);		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> c) {		
		
	}

	// пока что сделаю редактирование по клику на элемент списка
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ExHolder holder=(ExHolder) view.getTag();
		Intent intent=new Intent(getActivity(), EditEx.class);

		intent.putExtra(MODE_EDIT, true);
		intent.putExtra(ACTIVITY_TITLE, getString(R.string.text_edit));
		intent.putExtra(TITLE, holder.title.getText().toString());
		intent.putExtra(DESCRIPTION, holder.desc.getText().toString());
		intent.putExtra(MAX_COUNT, (int) holder.maxcount.getTag());
		intent.putExtra(TIME_LIM, (long) holder.timelim.getTag());
		intent.putExtra(ID, holder.id);
		startEditExActivity(intent);
	}
	
	void startEditExActivity(Intent intent){
		startActivityForResult(intent, 0);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==FragmentActivity.RESULT_OK){
			getLoaderManager().getLoader(0).forceLoad();
		}
	}
}
