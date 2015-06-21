package ru.vladislavkozhushko.shottimer;

import java.util.Locale;

import ru.vladislavkozhushko.data.ExSQLiteOpenHelper;
import ru.vladislavkozhushko.fragments.ExFragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class EditEx extends Activity implements OnCheckedChangeListener,
		android.view.View.OnClickListener {
	private Dialog mContShotsDialog, mTimeLimitDialog;
	private AlertDialog.Builder mBuilder;
	private EditText mEditTitle, mEditDescroption;
	private CheckBox mCountBox;
	private CheckBox mTimeBox;
	private TextView mConunTextView, mTimeLimitTextView;
	private int mMaxcount = 0;
	private long mTimeLimitMS = 0;
	private Intent mIntent;
	private ExSQLiteOpenHelper mDBHelper;
	private Button mEditButton;
	private boolean isModeEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mIntent = getIntent();
		mDBHelper = ExSQLiteOpenHelper.getInstance(EditEx.this);
		setContentView(R.layout.activity_edit_ex);
		mTimeBox = (CheckBox) findViewById(R.id.checkBoxTimeLimit);
		mCountBox = (CheckBox) findViewById(R.id.checkBoxCountLimit);
		mTimeLimitTextView = (TextView) findViewById(R.id.textTimeLimit);
		mConunTextView = (TextView) findViewById(R.id.textCountLimit);
		mEditTitle = (EditText) findViewById(R.id.editExTitle);
		mEditDescroption = (EditText) findViewById(R.id.editExDescription);
		mEditButton = (Button) findViewById(R.id.buttonEdit);
		mEditButton.setOnClickListener(this);
		if (isModeEdit = mIntent.getBooleanExtra(ExFragment.MODE_EDIT, false)) {
			mEditTitle.setText(mIntent.getStringExtra(ExFragment.TITLE));
			mEditDescroption.setText(mIntent
					.getStringExtra(ExFragment.DESCRIPTION));
			mMaxcount = mIntent.getIntExtra(ExFragment.MAX_COUNT, 0);
			if (mMaxcount > 0) {
				mCountBox.setChecked(true);
				mConunTextView.setText(String.valueOf(mMaxcount));
				mConunTextView.setVisibility(View.VISIBLE);
			}
			mTimeLimitMS = mIntent.getLongExtra(ExFragment.TIME_LIM, 0);
			if (mTimeLimitMS > 0) {
				mTimeBox.setChecked(true);
				mTimeLimitTextView.setText(String.format(Locale.US, "%.2f",
						mTimeLimitMS / 1000.0));
				mTimeLimitTextView.setVisibility(View.VISIBLE);
			}
		}
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
			np.setValue(5);
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
			sec_np.setValue((int)(mTimeLimitMS%1000));
			sec_np.setMinValue(1);
			sec_np.setSelected(false);
			final NumberPicker msec_np = (NumberPicker) v
					.findViewById(R.id.msecondsPicker); // пикер милисекунд
			msec_np.setMaxValue(99);
			msec_np.setValue((int)(mTimeLimitMS/1000));
			msec_np.setMinValue(0);
			msec_np.setSelected(false);
			mBuilder = new AlertDialog.Builder(this)
					.setView(v)
					.setCancelable(false)
					.setTitle(R.string.text_timelimit)
					.setPositiveButton(R.string.text_ok, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mTimeLimitMS = sec_np.getValue() * 1000
									+ msec_np.getValue()*10;
							mTimeLimitTextView.setText(String.format(Locale.US, "%.2f", mTimeLimitMS/1000.0));
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
				mMaxcount = 0;
				mCountBox.setChecked(false);
			}
			break;
		case R.id.checkBoxTimeLimit:
			if (isChecked) {
				showDialogTimeLimitPicker();
			} else {
				mTimeLimitTextView.setVisibility(View.INVISIBLE);
				mTimeLimitTextView.setText(null);
				mTimeLimitMS = 0;				
				mTimeBox.setChecked(false);
			}
			break;
		}
	}

	boolean updateData(boolean isEdit) {
		if (mEditTitle.length() == 0) {
			mEditTitle.setError(getString(R.string.text_title_error));
			return false;
		} else
			mEditTitle.setError(null);
		// Запрос не большой, наверное, нет смысла создавать новый поток
		if (isEdit) {
			int id = mIntent.getIntExtra(ExFragment.ID, 0);
			if (id == 0)
				return false;
			mDBHelper.updateEX(id, mEditTitle.getText().toString(),
					mEditDescroption.getText().toString(), mTimeLimitMS,
					mMaxcount, 2);
		} else{
			mDBHelper.insertEX(mEditTitle.getText().toString(), mEditDescroption.getText().toString(), mTimeLimitMS, mMaxcount, false);
		}
		return true;
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

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonEdit) {
				if (updateData(isModeEdit)) {
					setResult(RESULT_OK);
					Toast.makeText(getApplicationContext(),
							android.R.string.ok, Toast.LENGTH_SHORT).show();
					finish();
				}
		}
	}
}
