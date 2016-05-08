package deeplife.gcme.com.deeplife.Activities;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import deeplife.gcme.com.deeplife.Adapters.Countries_Adapter;
import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.Models.Country;
import deeplife.gcme.com.deeplife.Models.User;
import deeplife.gcme.com.deeplife.R;
import deeplife.gcme.com.deeplife.SyncService.SyncService;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;


public class Login extends AppCompatActivity{

    private static final String TAG = "Log_In";
    private  AlertDialog.Builder builder;
    private Spinner sp_countries;
	private ProgressDialog pDialog;
	private Context myContext;
    private ArrayList<Country> Countries;
    private int pos;

	EditText ed_phoneNumber, ed_password,Ed_Codes;
	Button bt_login, btn_register;
	private TextInputLayout inputLayoutPhone, inputLayoutPassword;
    private User Main_User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
        if(DeepLife.myDatabase.count(deeplife.gcme.com.deeplife.Database.Database.Table_COUNTRY)<10){
            getAll_Countries();
        }
	}

	public void init(){
		myContext = this;
        Countries = DeepLife.myDatabase.get_All_Country();
		ed_phoneNumber = (EditText) findViewById(R.id.login_phone);
		ed_password = (EditText) findViewById(R.id.login_password);
		bt_login = (Button) findViewById(R.id.btnLogin);
        Ed_Codes = (EditText) findViewById(R.id.login_code);
		btn_register = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        sp_countries = (Spinner) findViewById(R.id.login_countries_spinner);
        init_CountryList();
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.login_txtinput_phone);
		inputLayoutPassword = (TextInputLayout) findViewById(R.id.login_inputtxt_password);

		ed_phoneNumber.addTextChangedListener(new MyTextWatcher(ed_phoneNumber));
		ed_password.addTextChangedListener(new MyTextWatcher(ed_phoneNumber));
        Countries = DeepLife.myDatabase.get_All_Country();
        Main_User = new User();
		setButtonActions();
	}
    public void init_CountryList(){
        sp_countries.setAdapter(new Countries_Adapter(this, R.layout.countries_spinner, Countries));
        sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                Ed_Codes.setText(Countries.get(position).getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Ed_Codes.setText(Countries.get(0).getCode());
            }
        });
    }
    public void getAll_Countries(){
        final ProgressDialog myDialog = new ProgressDialog(this);
        myDialog.setTitle(R.string.app_name);
        myDialog.setMessage("Downloading Necessary Files ....");
        myDialog.show();
        List<Pair<String, String>> Send_Param;
        Send_Param = new ArrayList<Pair<String, String>>();
        Send_Param.add(new kotlin.Pair<String, String>("User_Name", ""));
        Send_Param.add(new kotlin.Pair<String, String>("User_Pass", ""));
        Send_Param.add(new kotlin.Pair<String, String>("Country", ""));
        Send_Param.add(new kotlin.Pair<String, String>("Service", "Meta_Data"));
        Send_Param.add(new kotlin.Pair<String, String>("Param", "[]"));

        Fuel.post(DeepLife.API_URL, Send_Param).responseString(new Handler<String>() {
            @Override
            public void success(Request request, Response response, String s) {
                try {
                    JSONObject myObject = (JSONObject) new JSONTokener(s).nextValue();
                    if (!myObject.isNull("Response")) {
                        JSONObject json_response = myObject.getJSONObject("Response");
                        if (!json_response.isNull("Country")) {
                            JSONArray json_countries = json_response.getJSONArray("Country");
                            SyncService.Add_Country(json_countries);
                            myDialog.cancel();
                            startActivity(getIntent());
                            finish();
                        }
                    }
                } catch (Exception e) {
                    ShowErrorDialog("Make sure to turn on your internet connection. then try again");
                }
            }

            @Override
            public void failure(Request request, Response response, FuelError fuelError) {
                ShowErrorDialog("Make sure to turn on your internet connection. then try again");
            }
        });
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

        Main_User.setUser_Phone(ed_phoneNumber.getText().toString());
        Main_User.setUser_Pass(ed_password.getText().toString());
        Main_User.setUser_Country(Countries.get(pos).getCountry_id());
        final ProgressDialog myDialog = new ProgressDialog(this);
        myDialog.setTitle(R.string.app_name);
        myDialog.setMessage("Authenticating the Account ....");
        myDialog.show();
        List<Pair<String, String>> Send_Param;
        Send_Param = new ArrayList<Pair<String, String>>();
        Send_Param.add(new kotlin.Pair<String, String>("User_Name", Main_User.getUser_Phone()));
        Send_Param.add(new kotlin.Pair<String, String>("User_Pass", Main_User.getUser_Pass()));
        Send_Param.add(new kotlin.Pair<String, String>("Country", Main_User.getUser_Country()));
        Send_Param.add(new kotlin.Pair<String, String>("Service", "Log_In"));
        Send_Param.add(new kotlin.Pair<String, String>("Param", "[]"));

        Fuel.post(DeepLife.API_URL, Send_Param).responseString(new Handler<String>() {
            @Override
            public void success(Request request, Response response, String s) {
                myDialog.cancel();
                try {
                    Log.i(TAG, "Server Request -> \n" + request.toString());
                    Log.i(TAG, "Server Response -> \n" + s);
                    JSONObject myObject = (JSONObject) new JSONTokener(s).nextValue();
                    if (!myObject.isNull("Response")) {

                        DeepLife.myDatabase.Delete_All(deeplife.gcme.com.deeplife.Database.Database.Table_DISCIPLES);
                        DeepLife.myDatabase.Delete_All(deeplife.gcme.com.deeplife.Database.Database.Table_SCHEDULES);
                        DeepLife.myDatabase.Delete_All(deeplife.gcme.com.deeplife.Database.Database.Table_LOGS);
                        DeepLife.myDatabase.Delete_All(deeplife.gcme.com.deeplife.Database.Database.Table_USER);



                        JSONObject json_response = myObject.getJSONObject("Response");
                        if(!json_response.isNull("Disciples")){
                            JSONArray json_Disciples = json_response.getJSONArray("Disciples");
                            SyncService.Add_Disciple(json_Disciples);
                        }
                        if(!json_response.isNull("Schedules")){
                            JSONArray json_schedules = json_response.getJSONArray("Schedules");
                            SyncService.Add_Schedule(json_schedules);
                        }
                        if(!json_response.isNull("Questions")){
                            JSONArray json_questions = json_response.getJSONArray("Questions");
                            SyncService.Add_Qustions(json_questions);
                        }
                        if(!json_response.isNull("Country")){
                            JSONArray json_countries = json_response.getJSONArray("Country");
                            SyncService.Add_Country(json_countries);
                        }
                        if(!json_response.isNull("Reports")){
                            JSONArray json_reports = json_response.getJSONArray("Reports");
                            SyncService.Add_Report_Forms(json_reports);
                        }
                        if(!json_response.isNull("Profile")){
                            JSONObject json_profile = json_response.getJSONObject("Profile");
                            SyncService.Add_UserProfile(json_profile);
                            ContentValues cv = new ContentValues();
                            cv.put(Database.USER_FIELDS[2],Main_User.getUser_Phone());
                            cv.put(Database.USER_FIELDS[4], Main_User.getUser_Pass());
                            User dis = DeepLife.myDatabase.get_User();
                            long x = DeepLife.myDatabase.update(Database.Table_USER, cv, Integer.valueOf(dis.getId()));
                            Log.i(TAG, "Update User Table-> \n" + x);
                            Intent main_activity = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(main_activity);
                            finish();
                        }

                    } else {
                        ShowDialog("Invalid user account! use a valid account please");
                    }
                } catch (Exception e) {
                    ShowDialog("Something went wrong! Please try again \n"+e.toString());
                    Log.i(TAG, "Error Occurred-> \n" + e.toString());
                }
            }

            @Override
            public void failure(Request request, Response response, FuelError fuelError) {
                Log.i(TAG, "Server Response -> \n" + response.toString());
                myDialog.cancel();
                ShowDialog("Authentication has failed! Please try again");
            }
        });
    }
    public void ShowDialog(String message) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        break;
                }
            }
        };
        builder = new AlertDialog.Builder(myContext);
        builder.setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton("Ok", dialogClickListener).show();
    }
    public void ShowErrorDialog(String message) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        startActivity(getIntent());
                        finish();
                        break;
                }
            }
        };
        builder = new AlertDialog.Builder(myContext);
        builder.setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton("Ok", dialogClickListener).show();
    }
    private boolean validatePhone() {
        if (ed_phoneNumber.getText().toString().trim().isEmpty() || ed_phoneNumber.getText().toString().length()>12 || ed_phoneNumber.getText().toString().length()<6) {
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
