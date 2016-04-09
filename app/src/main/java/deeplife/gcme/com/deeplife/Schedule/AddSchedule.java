package deeplife.gcme.com.deeplife.Schedule;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

import deeplife.gcme.com.deeplife.Database.DeepLife;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.Models.Disciples;
import deeplife.gcme.com.deeplife.R;
import deeplife.gcme.com.deeplife.SyncService.SyncService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddSchedule extends AppCompatActivity implements android.app.TimePickerDialog.OnTimeSetListener, android.app.DatePickerDialog.OnDateSetListener{

	private Toolbar toolbar;
	Spinner sp_name;
	EditText ed_descr,ed_title;

	TextView tv_hidden_id, tv_date,tv_time;

	Button btn_addSchedule, btn_date, btn_time;
	ArrayList<Disciples> list_names;
	String scheduled_disciple_id, disciple_phone;
	private TextInputLayout inputLayoutTitle, inputLayoutDesc;

	private Calendar mCalendar;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_add);
		toolbar = (Toolbar) findViewById(R.id.add_schedule_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Add a Schedule");
		init();

	}

	public void init(){
		mCalendar = Calendar.getInstance();
		sp_name = (Spinner) findViewById(R.id.schedule_add_name);
		ed_descr = (EditText) findViewById(R.id.schedule_add_disc);
		ed_title = (EditText) findViewById(R.id.schedule_add_title);
        tv_date = (TextView) findViewById(R.id.schedule_txt_date);
        tv_time = (TextView) findViewById(R.id.schedule_txt_time);
		tv_hidden_id = (TextView) findViewById(R.id.schedule_disciple_id);
		btn_addSchedule = (Button) findViewById(R.id.btn_add_schedule);
		btn_date = (Button) findViewById(R.id.schedule_btn_date);
		btn_time = (Button) findViewById(R.id.schedule_btn_time);

		inputLayoutTitle = (TextInputLayout) findViewById(R.id.add_schedule_inputtxt_title);
		inputLayoutDesc = (TextInputLayout) findViewById(R.id.add_schedule_inputtxt_desc);
        ed_title.addTextChangedListener(new MyTextWatcher(ed_title));
        ed_descr.addTextChangedListener(new MyTextWatcher(ed_descr));

		list_names = deeplife.gcme.com.deeplife.DeepLife.myDatabase.getDisciples();

		sp_name.setAdapter(new NameSpinnerAdapter(this, R.layout.countries_spinner, list_names));
		sp_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int i = sp_name.getSelectedItemPosition();
                scheduled_disciple_id = list_names.get(i).getId().toString();
				disciple_phone = list_names.get(i).getPhone().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                scheduled_disciple_id = list_names.get(0).getId().toString();
				disciple_phone = list_names.get(0).getPhone().toString();
			}
        });
		addScheduleHandler();
	}

	public void addScheduleHandler(){

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

		btn_addSchedule.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submitForm();
			}
		});

	}


    private void showDatePicker() {


        DatePickerDialog datePicker = new DatePickerDialog(AddSchedule.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText();
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    private void showTimePicker() {

        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                updateTimeText();
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
        timePicker.show();
    }
    private void updateTimeText() {
        // Set the time button text based upon the value from the database
        SimpleDateFormat timeFormat = new SimpleDateFormat(ScheduleListFragment.TIME_FORMAT);
        String timeForButton = timeFormat.format(mCalendar.getTime());
        tv_time.setText(timeForButton);
    }
    private void updateDateText() {
        // Set the date button text based upon the value from the database
        SimpleDateFormat dateFormat = new SimpleDateFormat(ScheduleListFragment.DATE_FORMAT);
        String dateForButton = dateFormat.format(mCalendar.getTime());
        tv_date.setText(dateForButton);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

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

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(ScheduleListFragment.DATE_TIME_FORMAT);
		String reminderDateTime = dateTimeFormat.format(mCalendar.getTime());

		ContentValues values = new ContentValues();
		values.put(DeepLife.SCHEDULES_FIELDS[0], disciple_phone);
		values.put(DeepLife.SCHEDULES_FIELDS[1], ed_title.getText().toString());
		values.put(DeepLife.SCHEDULES_FIELDS[2], reminderDateTime);
		values.put(DeepLife.SCHEDULES_FIELDS[3], "Normal");
		values.put(DeepLife.SCHEDULES_FIELDS[4], ed_descr.getText().toString());
		long i = deeplife.gcme.com.deeplife.DeepLife.myDatabase.insert(DeepLife.Table_SCHEDULES, values);
		if (i != -1) {
			new ReminderManager(this).setReminder(i, mCalendar);

			ContentValues log = new ContentValues();
			log.put(deeplife.gcme.com.deeplife.Database.DeepLife.LOGS_FIELDS[0], "Schedule");
			log.put(deeplife.gcme.com.deeplife.Database.DeepLife.LOGS_FIELDS[1], SyncService.Sync_Tasks[4]);
			log.put(deeplife.gcme.com.deeplife.Database.DeepLife.LOGS_FIELDS[2], i);
			deeplife.gcme.com.deeplife.DeepLife.myDatabase.insert(deeplife.gcme.com.deeplife.Database.DeepLife.Table_LOGS, log);

			Toast.makeText(getApplicationContext(), "Alarm Successfully Added!", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AddSchedule.this, MainActivity.class);
			startActivity(intent);
			finish();
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
