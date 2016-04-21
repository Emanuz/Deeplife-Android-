package deeplife.gcme.com.deeplife.Activities;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.R;
import deeplife.gcme.com.deeplife.SyncService.SyncService;


public class AddTestimony extends AppCompatActivity {

	private Toolbar toolbar;
	EditText ed_title, ed_detail;
	private TextInputLayout inputLayoutTitle, inputLayoutDetail;
	Button addTestimony;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_testimony);
		toolbar = (Toolbar) findViewById(R.id.add_disciple_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Add your Disciple");
		init();

	}


	public void init(){
		ed_title = (EditText) findViewById(R.id.et_testimony_title);
		ed_detail = (EditText) findViewById(R.id.et_testimony_detail);
		addTestimony = (Button) findViewById(R.id.btn_testimony_add);

		inputLayoutTitle = (TextInputLayout) findViewById(R.id.testimony_inputtxt_title);
		inputLayoutDetail = (TextInputLayout) findViewById(R.id.testimony_inputtxt_detail);

		ed_title.addTextChangedListener(new MyTextWatcher(ed_title));
		ed_detail.addTextChangedListener(new MyTextWatcher(ed_detail));

		addTestimonyHandler();
	}

	public void addTestimonyHandler(){
        addTestimony.setOnClickListener(new View.OnClickListener() {

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


	private void submitForm() {
		if (!validateTitle()) {
			return;
		}

		if (!validateDetail()) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(Database.TESTIMONY_FIELDS[0], ed_title.getText().toString());
		values.put(Database.TESTIMONY_FIELDS[1], ed_detail.getText().toString());

		long i = deeplife.gcme.com.deeplife.DeepLife.myDatabase.insert(Database.Table_TESTIMONY, values);
		if(i!=-1){
			Log.i(Database.TAG, "Successfully Added new Testimony \n Values: " + values.toString());
			Toast.makeText(getApplicationContext(), "New Testimony Successfully Sent!!", Toast.LENGTH_SHORT).show();
			ContentValues log = new ContentValues();
			log.put(Database.LOGS_FIELDS[0],"Testimony");
			log.put(Database.LOGS_FIELDS[1], SyncService.Sync_Tasks[6]);
			log.put(Database.LOGS_FIELDS[2], i);
			deeplife.gcme.com.deeplife.DeepLife.myDatabase.insert(Database.Table_LOGS, log);
			Intent intent = new Intent(AddTestimony.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
        else {
            Toast.makeText(this,"Could not send Testimony. \nPlease try again",Toast.LENGTH_SHORT).show();
        }
	}

	private boolean validateTitle() {
		if (ed_title.getText().toString().trim().isEmpty()) {
			inputLayoutTitle.setError(getString(R.string.err_msg_testimony_title));
			requestFocus(ed_title);
			return false;
		} else {
			inputLayoutTitle.setErrorEnabled(false);
		}
		return true;
	}


	private boolean validateDetail() {
		if (ed_detail.getText().toString().trim().isEmpty()) {
			inputLayoutDetail.setError(getString(R.string.err_msg_testimony_title));
			requestFocus(ed_detail);
			return false;
		} else {
			inputLayoutDetail.setErrorEnabled(false);
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
				case R.id.et_testimony_title:
					validateTitle();
					break;
				case R.id.et_testimony_detail:
					validateDetail();
					break;
			}
		}
	}
}
