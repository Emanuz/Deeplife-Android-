package com.gcme.deeplife.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.gcme.deeplife.Models.CountryDetails;
import com.gcme.deeplife.R;


public class Register extends AppCompatActivity{


	/*
           Variables declaration
    */

	private Context myContext;

	private Button  Register;
	private EditText Full_Name,Email,Phone,Country,Pass,Ed_Codes;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;

    private Spinner sp_countries, sp_gender;
	private ProgressDialog pDialog;
	public static String[] list =  CountryDetails.country;
	public static String[] codes = CountryDetails.code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		myContext = this;

        Init();

	}


	public void Init() {

        Full_Name = (EditText) findViewById(R.id.signup_first_name);
        Email = (EditText) findViewById(R.id.signup_email);
        Phone = (EditText) findViewById(R.id.signup_phone);
        Pass = (EditText) findViewById(R.id.signup_password);
        Ed_Codes = (EditText) findViewById(R.id.signup_code);
        Register = (Button) findViewById(R.id.btnregister);
        sp_gender = (Spinner) findViewById(R.id.register_gender_spinner);
        sp_countries = (Spinner) findViewById(R.id.signup_countries_spinner);


        inputLayoutName = (TextInputLayout) findViewById(R.id.signup_txtinput_firstname);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.signup_txtinput_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.signup_txtinput_password);

        Full_Name.addTextChangedListener(new MyTextWatcher(Full_Name));
        Email.addTextChangedListener(new MyTextWatcher(Email));
        Pass.addTextChangedListener(new MyTextWatcher(Pass));

        /*
            Fill the spinners with data from Country name and code pair.
        **/

        spinnerInit();

    }

    public void spinnerInit() {
        sp_countries.setAdapter(new MySpinnerAdapter(this, R.layout.countries_spinner, list));
        sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int i = sp_countries.getSelectedItemPosition();
                Ed_Codes.setText(codes[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Ed_Codes.setText(codes[0]);
            }
        });

        /*
        * handle register button
        *
        * */
        registerHandler();
    }

    public void registerHandler(){
		Register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

                submitForm();
				// TODO Auto-generated method stub

/*				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Task", "Register"));
				params.add(new BasicNameValuePair("Full_Name", Full_Name.getText().toString()));
				params.add(new BasicNameValuePair("Password", Pass.getText().toString()));
				params.add(new BasicNameValuePair("Email", Email.getText().toString()));
				params.add(new BasicNameValuePair("Phone", Ed_Codes.getText().toString() + Phone.getText().toString()));
				params.add(new BasicNameValuePair("Build_phase", "Added"));
				params.add(new BasicNameValuePair("Gender", sp_gender.getSelectedItem().toString()));
				params.add(new BasicNameValuePair("Build_phase", Phone.getText().toString()));
				params.add(new BasicNameValuePair("Picture", null));
				params.add(new BasicNameValuePair("Country", sp_countries.getSelectedItem().toString()));
				new Make_Request(params).execute();
				Log.i("Sync_Service", "Registering user");
*/

			}
		});
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


    private class MySpinnerAdapter extends ArrayAdapter<String> {

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

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (Full_Name.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(Full_Name);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = Email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(Email);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (Pass.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(Pass);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
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
                case R.id.signup_first_name:
                    validateName();
                    break;
                case R.id.signup_email:
                    validateEmail();
                    break;
                case R.id.signup_password:
                    validatePassword();
                    break;
            }
        }
    }
}
