package ru.vladislavkozhushko.shottimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.NumberPicker;
import android.widget.TextView;

public class EditEx extends Activity implements OnCheckedChangeListener {
	private Dialog mContShotsDialog, mTimeLimitDialog;
	private AlertDialog.Builder mBuilder;
	private CheckBox mCountBox;
	private CheckBox mTimeBox;
	private TextView mConunTextView, mTimeLimitTextView;
	private int mMaxcount = 0;
	private long mTimeLimitMS = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_edit_ex);
		mTimeBox = (CheckBox) findViewById(R.id.checkBoxTimeLimit);
		mCountBox = (CheckBox) findViewById(R.id.checkBoxCountLimit);
		mTimeLimitTextView = (TextView) findViewById(R.id.textTimeLimit);
		mConunTextView = (TextView) findViewById(R.id.textCountLimit);
		mTimeBox.setOnCheckedChangeListener(this);
		mCountBox.setOnCheckedChangeListener(this);
	}

	private void showDialogCountPicker() {
		if (mContShotsDialog == null) {
			View v = getLayoutInflater().inflate(R.layout.numberpicker, null);
			final NumberPicker np = (NumberPicker) v
					.findViewById(R.id.numberPicker1);
			np.setMaxValue(100);
			np.setMinValue(1);
			np.setSelected(false);
			mBuilder = new AlertDialog.Builder(this)
					.setView(v)
					.setCancelable(false)
					.setTitle(R.string.text_shotsCount)
					.setPositiveButton(R.string.text_ok, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mConunTextView.setText(String
									.valueOf(mMaxcount = np.getValue()));
							mConunTextView.setVisibility(View.VISIBLE);
						}
					})
					.setNegativeButton(R.string.text_cancel,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									mConunTextView
											.setVisibility(View.INVISIBLE);
									mConunTextView.setText(String
											.valueOf(mMaxcount = 0));
									mCountBox.setChecked(false);
									dialog.cancel();
								}
							});
			mContShotsDialog = mBuilder.create();
		}
		mContShotsDialog.show();
	}

	private void showDialogTimeLimitPicker() {
		if (mTimeLimitDialog == null) {
			View v = getLayoutInflater().inflate(R.layout.secmsecpicker, null);
			final NumberPicker sec_np = (NumberPicker) v
					.findViewById(R.id.seconsPicker);// пикер секунд
			sec_np.setMaxValue(60);
			sec_np.setValue(2);
			sec_np.setMinValue(1);
			sec_np.setSelected(false);
			final NumberPicker msec_np = (NumberPicker) v
					.findViewById(R.id.msecondsPicker); // пикер милисекунд
			msec_np.setMaxValue(99);
			msec_np.setMinValue(0);
			msec_np.setSelected(false);
			mBuilder = new AlertDialog.Builder(this)
					.setView(v)
					.setCancelable(false)
					.setTitle(R.string.text_timelimit)
					.setPositiveButton(R.string.text_ok, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mTimeLimitMS = sec_np.getValue() * 100
									+ msec_np.getValue();
							mTimeLimitTextView.setText(String.valueOf(sec_np
									.getValue()));
							mTimeLimitTextView.append(".");
							mTimeLimitTextView.append(String.valueOf(msec_np
									.getValue()));
							mTimeLimitTextView.setVisibility(View.VISIBLE);
						}
					})
					.setNegativeButton(R.string.text_cancel,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									mTimeLimitTextView
											.setVisibility(View.INVISIBLE);
									mTimeLimitTextView.setText(String
											.valueOf(mMaxcount = 0));
									mTimeBox.setChecked(false);
									dialog.cancel();
								}
							});
			mTimeLimitDialog = mBuilder.create();
		}
		mTimeLimitDialog.setCanceledOnTouchOutside(false);
		mTimeLimitDialog.show();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBoxCountLimit:
			if (isChecked) {
				showDialogCountPicker();
			} else {
				mConunTextView.setVisibility(View.INVISIBLE);
				mConunTextView.setText(null);
				mCountBox.setChecked(false);
			}
			break;
		case R.id.checkBoxTimeLimit:
			if (isChecked) {
				showDialogTimeLimitPicker();
			} else {
				mTimeLimitTextView.setVisibility(View.INVISIBLE);
				mTimeLimitTextView.setText(null);
				mTimeBox.setChecked(false);
			}
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
