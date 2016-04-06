package com.gcme.deeplife.Activities;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gcme.deeplife.Adapters.Countries_Adapter;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.MainActivity;
import com.gcme.deeplife.Models.Country;
import com.gcme.deeplife.R;
import com.gcme.deeplife.SyncService.SyncService;

import java.util.ArrayList;


public class AddDisciple extends AppCompatActivity {

	private Toolbar toolbar;
	EditText ed_name, ed_email,ed_phone,ed_codes;
	private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPhone;
	Spinner sp_countries, sp_gender;
	Button addDisciple;
	private ArrayList<Country> Countries;
	private int Pos;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_add_disciple);
		toolbar = (Toolbar) findViewById(R.id.add_disciple_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Add your Disciple");
		Countries = com.gcme.deeplife.DeepLife.myDatabase.get_All_Country();
		init();

	}


	public void init(){
		ed_name = (EditText) findViewById(R.id.adddisciple_name);
		ed_email = (EditText) findViewById(R.id.add_discple_email);
		ed_phone = (EditText) findViewById(R.id.add_disciple_phone);
		ed_codes = (EditText) findViewById(R.id.add_disciple_phone_country_code);
		addDisciple = (Button) findViewById(R.id.btn_add_disciple);
		sp_countries = (Spinner) findViewById(R.id.add_disciple_countries_spinner);
		sp_gender = (Spinner) findViewById(R.id.add_disciple_gender_spinner);

		inputLayoutName = (TextInputLayout) findViewById(R.id.add_disciple_inputtxt_name);
		inputLayoutEmail = (TextInputLayout) findViewById(R.id.add_disciple_inputtxt_email);
		inputLayoutPhone = (TextInputLayout) findViewById(R.id.add_disciple_inputtxt_phone);


		ed_name.addTextChangedListener(new MyTextWatcher(ed_name));
		ed_email.addTextChangedListener(new MyTextWatcher(ed_email));
		ed_phone.addTextChangedListener(new MyTextWatcher(ed_phone));

		sp_countries.setAdapter(new Countries_Adapter(this, R.layout.countries_spinner, Countries));
		sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Pos = position;
				ed_codes.setText(Countries.get(position).getCode());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Pos = 0;
				ed_codes.setText(Countries.get(0).getCode());
			}
		});

		addDiscipleHandler();
	}

	public void addDiscipleHandler(){
        addDisciple.setOnClickListener(new View.OnClickListener() {

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

	public class MySpinnerAdapter extends ArrayAdapter<String> {

		String[] object;
		public MySpinnerAdapter(Context ctx, int txtViewResourceId, String[] objects) {
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
			main_text.setText(object[position]);

			return mySpinner;
		}
	}


	private void submitForm() {
		if (!validateName()) {
			return;
		}

		if (!validateEmail()) {
			return;
		}

		if (!validatePhone()) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(DeepLife.DISCIPLES_FIELDS[0], ed_name.getText().toString());
		values.put(DeepLife.DISCIPLES_FIELDS[1], ed_email.getText().toString());
		values.put(DeepLife.DISCIPLES_FIELDS[2], ed_phone.getText().toString());
		values.put(DeepLife.DISCIPLES_FIELDS[3], Countries.get(Pos).getCountry_id());
		values.put(DeepLife.DISCIPLES_FIELDS[4], "Added");
		values.put(DeepLife.DISCIPLES_FIELDS[5], sp_gender.getSelectedItem().toString());
		long i = com.gcme.deeplife.DeepLife.myDatabase.insert(DeepLife.Table_DISCIPLES, values);
		if(i!=-1){
			Log.i(DeepLife.TAG, "Successfully Added new Disciple \n Values: " + values.toString());
			Toast.makeText(getApplicationContext(), "New Disciple Successfully Added!!", Toast.LENGTH_SHORT).show();
			ContentValues log = new ContentValues();
			log.put(DeepLife.LOGS_FIELDS[0],"Disciple");
			log.put(DeepLife.LOGS_FIELDS[1], SyncService.Sync_Tasks[1]);
			log.put(DeepLife.LOGS_FIELDS[2], i);
			com.gcme.deeplife.DeepLife.myDatabase.insert(DeepLife.Table_LOGS, log);
			Intent intent = new Intent(AddDisciple.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private boolean validateName() {
		if (ed_name.getText().toString().trim().isEmpty()) {
			inputLayoutName.setError(getString(R.string.err_msg_name));
			requestFocus(ed_name);
			return false;
		} else {
			inputLayoutName.setErrorEnabled(false);
		}

		return true;
	}

	private boolean validateEmail() {
		String email = ed_email.getText().toString().trim();
		if(email != null){
			if (email.isEmpty() || !isValidEmail(email)) {
				inputLayoutEmail.setError(getString(R.string.err_msg_email));
				requestFocus(ed_email);
				return false;
			} else {
				inputLayoutEmail.setErrorEnabled(false);
			}
		}else{
			ed_email.setText("");
		}
		return true;
	}

	private boolean validatePhone() {
		String phone = ed_phone.getText().toString();
		if (phone.trim().isEmpty() || phone.length() > 10 || phone.length()<8) {
			inputLayoutPhone.setError(getString(R.string.err_msg_phone));
			requestFocus(ed_phone);
			return false;
		} else {
			inputLayoutPhone.setErrorEnabled(false);
		}

		return true;
	}

	private static boolean isValidEmail(String email) {
		return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
				case R.id.adddisciple_name:
					validateName();
					break;
				case R.id.add_discple_email:
					validateEmail();
					break;
				case R.id.add_disciple_phone:
					validatePhone();
					break;
			}
		}
	}
}
