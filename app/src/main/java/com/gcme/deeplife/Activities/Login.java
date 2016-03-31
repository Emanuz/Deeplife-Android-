package com.gcme.deeplife.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gcme.deeplife.MainActivity;
import com.gcme.deeplife.R;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;


public class Login extends AppCompatActivity{

	private static final String LOGIN_URL = "http://api.cccsea.org/API.php";

	private ProgressDialog pDialog;
	private Context myContext;

	EditText ed_phoneNumber, ed_password;
	Button bt_login, btn_register;
	private TextInputLayout inputLayoutPhone, inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		init();

	}

	public void init(){

		myContext = this;
		ed_phoneNumber = (EditText) findViewById(R.id.login_phone);
		ed_password = (EditText) findViewById(R.id.login_password);
		bt_login = (Button) findViewById(R.id.btnLogin);
		btn_register = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        inputLayoutPhone = (TextInputLayout) findViewById(R.id.login_inputtxt_phone);
		inputLayoutPassword = (TextInputLayout) findViewById(R.id.login_inputtxt_password);

		ed_phoneNumber.addTextChangedListener(new MyTextWatcher(ed_phoneNumber));
		ed_password.addTextChangedListener(new MyTextWatcher(ed_phoneNumber));

		setButtonActions();
	}

	public void setButtonActions(){

		/*
				Button login works
		*/

		bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }


        });

		/*
				Button login works
		*/

		btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), Register.class);
                startActivity(register);
            }
        });

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


    private void submitForm() {

        if (!validatePhone()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        List<Pair<String, String>> Send_Param;
        Send_Param = new ArrayList<Pair<String, String>>();
        Send_Param.add(new kotlin.Pair<String, String>("User_Name", ed_phoneNumber.getText().toString()));
        Send_Param.add(new kotlin.Pair<String, String>("User_Pass", ed_password.getText().toString()));
        Send_Param.add(new kotlin.Pair<String, String>("Service", "Log_In"));
        Send_Param.add(new kotlin.Pair<String, String>("Param", "[]"));
        Toast.makeText(this,"Loging Started",Toast.LENGTH_LONG).show();
        Fuel.post("http://192.168.0.32/SyncSMS/public/deep_api", Send_Param).responseString(new Handler<String>() {
            @Override
            public void success(Request request, Response response, String s) {
                try {
                    JSONObject myObject = (JSONObject) new JSONTokener(s).nextValue();
                    Log.i("Log_In", "Server Response -> \n" + myObject.toString());
                    if (!myObject.isNull("Response")) {
                        Intent register = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(register);
                    }else{
                        Toast.makeText(Login.this, "Invalid User Account used", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(Login.this,"Error Occured\n"+e.toString(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void failure(Request request, Response response, FuelError fuelError) {
                Toast.makeText(Login.this, "Invalid User Account used", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validatePhone() {
        if (ed_phoneNumber.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(ed_phoneNumber);

            return false;
        }
        else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePassword() {
        if (ed_password.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(ed_password);
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
                case R.id.login_phone:
                    validatePhone();
                    break;
                case R.id.login_password:
                    validatePassword();
                    break;
            }
        }
    }
}
