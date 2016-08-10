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

import deeplife.gcme.com.deeplife.Adapters.Countries_Adapter;
import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.Models.CountryDetails;
import deeplife.gcme.com.deeplife.Models.User;
import deeplife.gcme.com.deeplife.R;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;


public class Register extends AppCompatActivity{


	/*
           Variables declaration
    */

	private Context myContext;
    private static final String TAG = "Register";
	private Button  Register;
	private EditText Full_Name,Email,Phone,Country,Pass,Ed_Codes;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword,inputLayoutPhone;
    private  AlertDialog.Builder builder;
    private User New_User;
    private Gson myParser;
    private Spinner sp_countries, sp_gender;
	private ProgressDialog pDialog;
	public static String[] list =  CountryDetails.country;
	public static String[] codes = CountryDetails.code;
    private ArrayList<deeplife.gcme.com.deeplife.Models.Country> Countries;
    private int Pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
        myParser = new Gson();
		myContext = this;
        Countries = DeepLife.myDatabase.get_All_Country();
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
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.signup_txtinput_phone);

        Full_Name.addTextChangedListener(new MyTextWatcher(Full_Name));
        Email.addTextChangedListener(new MyTextWatcher(Email));
        Pass.addTextChangedListener(new MyTextWatcher(Pass));




        /*
            Fill the spinners with data from Country name and code pair.
        **/

        spinnerInit();

    }

    public void spinnerInit() {
        sp_countries.setAdapter(new Countries_Adapter(this, R.layout.countries_spinner, Countries));
        sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Pos = position;
                Ed_Codes.setText(Countries.get(position).getCode());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Ed_Codes.setText(Countries.get(0).getCode());
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
        if (!validatePhone()) {
            return;
        }
        New_User = new User();
        New_User.setUser_Name(Full_Name.getText().toString());
        New_User.setUser_Pass(Pass.getText().toString());
        New_User.setUser_Country(Countries.get(Pos).getCountry_id());
        New_User.setUser_Phone(Phone.getText().toString());
        New_User.setUser_Email(Email.getText().toString());
        New_User.setUser_Gender(sp_gender.getSelectedItem().toString());


        final ProgressDialog myDialog = new ProgressDialog(this);
        myDialog.setTitle(R.string.app_name);
        myDialog.setMessage("Authenticating the Account ....");
        myDialog.show();
        ArrayList<User> user = new ArrayList<User>();
        user.add(New_User);
        List<Pair<String, String>> Send_Param;
        Send_Param = new ArrayList<Pair<String, String>>();
        Send_Param.add(new kotlin.Pair<String, String>("User_Name", New_User.getUser_Phone()));
        Send_Param.add(new kotlin.Pair<String, String>("User_Pass", New_User.getUser_Pass()));
        Send_Param.add(new kotlin.Pair<String, String>("Country", New_User.getUser_Country()));
        Send_Param.add(new kotlin.Pair<String, String>("Service", "Sign_Up"));
        Send_Param.add(new kotlin.Pair<String, String>("Param", myParser.toJson(user)));
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

                        ContentValues cv = new ContentValues();
                        cv.put(deeplife.gcme.com.deeplife.Database.Database.USER_FIELDS[0],New_User.getUser_Name());
                        cv.put(deeplife.gcme.com.deeplife.Database.Database.USER_FIELDS[1],New_User.getUser_Email());
                        cv.put(deeplife.gcme.com.deeplife.Database.Database.USER_FIELDS[2],New_User.getUser_Phone());
                        cv.put(deeplife.gcme.com.deeplife.Database.Database.USER_FIELDS[3],New_User.getUser_Pass());
                        cv.put(deeplife.gcme.com.deeplife.Database.Database.USER_FIELDS[4], New_User.getUser_Country());
                        cv.put(deeplife.gcme.com.deeplife.Database.Database.USER_FIELDS[5],"");
                        cv.put(deeplife.gcme.com.deeplife.Database.Database.USER_FIELDS[6],"");
                        long x = DeepLife.myDatabase.insert(deeplife.gcme.com.deeplife.Database.Database.Table_USER, cv);
                        Log.i(TAG, "Main User Adding-> " + x);

                        Intent register = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(register);
                        finish();

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

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
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
    private boolean validatePhone() {
        String phone = Phone.getText().toString().trim();
        if (phone.isEmpty() || phone.length()>10 || phone.length()<8) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(Phone);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
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
