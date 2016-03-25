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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gcme.deeplife.R;


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
                Intent register = new Intent(getApplicationContext(), AddDisciple.class);
                startActivity(register);
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

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
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
