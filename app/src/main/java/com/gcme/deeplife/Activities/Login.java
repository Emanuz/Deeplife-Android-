package com.gcme.deeplife.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gcme.deeplife.R;


public class Login extends AppCompatActivity{

	private static final String LOGIN_URL = "http://api.cccsea.org/API.php";

	private ProgressDialog pDialog;
	private Context myContext;

	EditText ed_phoneNumber;
	EditText ed_password;
	Button bt_login;
	Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		myContext = this;
		ed_phoneNumber = (EditText) findViewById(R.id.login_phone);
		ed_password = (EditText) findViewById(R.id.login_password);
		bt_login = (Button) findViewById(R.id.btnLogin);
		btn_register = (Button) findViewById(R.id.btnLinkToRegisterScreen);


		/*
				Button login works
		*/

		bt_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					Toast.makeText(Login.this, "Login Button touched", Toast.LENGTH_SHORT).show();
				}catch (Exception e){
					//
				}

			}
		});

		/*
				Button login works
		*/

		btn_register.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v){
				Intent register = new Intent(getApplicationContext(), Register.class);
				startActivity(register);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
