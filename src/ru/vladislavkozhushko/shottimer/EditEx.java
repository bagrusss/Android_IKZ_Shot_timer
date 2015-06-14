package ru.vladislavkozhushko.shottimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.NumberPicker;
import android.widget.TextView;

public class EditEx extends Activity implements OnCheckedChangeListener {
	private Dialog mDialog;
	private AlertDialog.Builder mBuilder;
	private CheckBox mCountBox;
	private CheckBox mTimeBox;
	private TextView mConunTextView, mTimeLimitTextView;
	private int maxcount=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_ex);
		mTimeBox=(CheckBox) findViewById(R.id.checkBoxTimeLimit);
		mCountBox=(CheckBox) findViewById(R.id.checkBoxCountLimit);
		mTimeLimitTextView=(TextView) findViewById(R.id.textCountLimit);
		mCountBox.setOnCheckedChangeListener(this);
		// np.setWrapSelectorWheel(false);
	}

	void showDialogCountPicker() {
		View v=getLayoutInflater().inflate(R.layout.numberpicker, null);
		final NumberPicker np=(NumberPicker) v.findViewById(R.id.numberPicker1);
		np.setMaxValue(100);
		np.setMinValue(1);
		mBuilder = new Builder(this).setView(v).setTitle(R.string.text_shotsCount).setPositiveButton(R.string.text_ok, new OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mTimeLimitTextView.setText(String.valueOf(maxcount=np.getValue()));
						mTimeLimitTextView.setVisibility(View.VISIBLE);
					}
				}).setNegativeButton(R.string.text_cancel, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mTimeLimitTextView.setVisibility(View.INVISIBLE);
						mTimeLimitTextView.setText(String.valueOf(maxcount=0));
						mCountBox.setChecked(false);
						dialog.cancel();						
					}
				});
		mDialog=mBuilder.create();
		mDialog.show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_ex, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()){
		case R.id.checkBoxCountLimit:
			if (isChecked){
				showDialogCountPicker();
			} else{
				mTimeLimitTextView.setVisibility(View.INVISIBLE);
				mTimeLimitTextView.setText(String.valueOf(maxcount=0));
				mCountBox.setChecked(false);
			}
			break;
		}
		
	}

}
