package com.gcme.deeplife.Activities;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.MainActivity;
import com.gcme.deeplife.Models.Disciples;
import com.gcme.deeplife.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddSchedule extends AppCompatActivity {

	private Toolbar toolbar;
	Spinner sp_name;
	EditText ed_descr,ed_title;
	DatePicker dp_date;
	TimePicker tp_time;
	TextView tv_hidden_id;
	Database myBD;

	Button btn_addSchedule;
	ArrayList<Disciples> list_names;
	String scheduled_disciple_id;
	private TextInputLayout inputLayoutTitle, inputLayoutDesc;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_add_disciple);
		toolbar = (Toolbar) findViewById(R.id.add_disciple_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Add a Schedule");
		init();

	}


	public void init(){
		myBD = new Database(this);
		sp_name = (Spinner) findViewById(R.id.schedule_add_name);
		ed_descr = (EditText) findViewById(R.id.schedule_add_disc);
		ed_title = (EditText) findViewById(R.id.schedule_add_title);
		dp_date = (DatePicker) findViewById(R.id.schedule_add_date);
		tp_time = (TimePicker) findViewById(R.id.schedule_add_time_picker);
		tv_hidden_id = (TextView) findViewById(R.id.schedule_disciple_id);
		btn_addSchedule = (Button) findViewById(R.id.btn_add_schedule);
		inputLayoutTitle = (TextInputLayout) findViewById(R.id.add_schedule_inputtxt_title);
		inputLayoutDesc = (TextInputLayout) findViewById(R.id.add_schedule_inputtxt_desc);
		list_names = myBD.getDisciples();

		sp_name.setAdapter(new NameSpinnerAdapter(this, R.layout.countries_spinner, list_names));
		sp_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int i = sp_name.getSelectedItemPosition();
				scheduled_disciple_id = list_names.get(i).getId().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				scheduled_disciple_id = list_names.get(0).getId().toString();
			}
		});
		addScheduleHandler();
	}

	public void addScheduleHandler(){

		btn_addSchedule.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submitForm();
			}
		});


	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public class NameSpinnerAdapter extends ArrayAdapter<Disciples> {

		ArrayList<Disciples> object;
		public NameSpinnerAdapter(Context ctx, int txtViewResourceId, ArrayList<Disciples> objects) {
			super(ctx, txtViewResourceId, objects);
			this.object = objects;
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}
		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}
		public View getCustomView(int position, View convertView,
								  ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.countries_spinner, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.spinner_text);
			String name = object.get(position).getFull_Name().toString();
			Log.i(DeepLife.TAG,"name = " +name);
			if(name!=null){
				main_text.setText(name);
			}
			return mySpinner;
		}
	}


	private void submitForm() {
		if (!validateTitle()) {
			return;
		}

		if (!validateDesc()) {
			return;
		}


		String time = tp_time.getCurrentHour().toString() + tp_time.getCurrentMinute();

		//get date
		int day = dp_date.getDayOfMonth();
		int month = dp_date.getMonth();
		int year =  dp_date.getYear();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		calendar.set(Calendar.HOUR_OF_DAY, tp_time.getCurrentHour());
		calendar.set(Calendar.MINUTE,tp_time.getCurrentMinute());
		Date date = calendar.getTime();

		ContentValues values = new ContentValues();
		values.put(DeepLife.SCHEDULES_FIELDS[0], scheduled_disciple_id);
		values.put(DeepLife.SCHEDULES_FIELDS[1], ed_title.getText().toString());
		values.put(DeepLife.SCHEDULES_FIELDS[2], time);
		values.put(DeepLife.SCHEDULES_FIELDS[3],0);
		values.put(DeepLife.SCHEDULES_FIELDS[3], ed_descr.getText().toString());

		Toast.makeText(getApplicationContext(),"Alarm Set",Toast.LENGTH_LONG).show();
		long i = myBD.insert(DeepLife.Table_SCHEDULES,values);
		if(i!=-1){
			//insert the disciple to log table
			ContentValues cv1 = new ContentValues();
			cv1.put(DeepLife.LOGS_FIELDS[0], "Send_Schedule");
			cv1.put(DeepLife.LOGS_FIELDS[1], myBD.get_Value_At_Bottom(DeepLife.Table_SCHEDULES, DeepLife.SCHEDULES_COLUMN[0]));
			if(myBD.insert(DeepLife.Table_LOGS, cv1)!=-1){

			}
			Toast.makeText(getApplicationContext(),"Alarm Successfully Added!",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AddSchedule.this,MainActivity.class);
			startActivity(intent);
		}

	}

	private boolean validateTitle() {
		if (ed_title.getText().toString().trim().isEmpty()) {
			inputLayoutTitle.setError(getString(R.string.err_msg_name));
			requestFocus(ed_title);
			return false;
		} else {
			inputLayoutTitle.setErrorEnabled(false);
		}

		return true;
	}


	private boolean validateDesc() {
		if (ed_descr.getText().toString().trim().isEmpty()) {
			inputLayoutDesc.setError(getString(R.string.err_msg_phone));
			requestFocus(ed_descr);
			return false;
		} else {
			inputLayoutDesc.setErrorEnabled(false);
		}

		return true;
	}


	private void requestFocus(View view) {
		if (view.requestFocus()) {
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		}
	}

	private class MyTextWatcher implements TextWatcher {

		private View view;

		private MyTextWatcher(View view) {
			this.view = view;
		}

		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		}

		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		}

		public void afterTextChanged(Editable editable) {
			switch (view.getId()) {
				case R.id.schedule_add_title:
					validateTitle();
					break;
				case R.id.schedule_add_disc:
					validateDesc();
					break;

			}
		}
	}
}
