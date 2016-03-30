package com.gcme.deeplife.Activities.UserProfile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.ImageProcessing.ImageProcessing;
import com.gcme.deeplife.Models.CountryDetails;
import com.gcme.deeplife.Models.User;
import com.gcme.deeplife.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class User_Profile_Edit extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView profile_image;
    EditText tv_name,tv_email, tv_fav_scripture, tv_phone;
    private Spinner sp_countries;
    TextView tv_country_code;
    Button btn_save;
    ImageButton btn_image_pciker;


    public String newImage;
    Bitmap imageFromCrop = null;
    Database myDB;
    int user_id;
    Activity activity;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_page_edit);
        setSupportActionBar((Toolbar) findViewById(R.id.user_profile_edit_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDB = new Database(this);
        user_id = myDB.get_Top_ID(DeepLife.Table_USER);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.user_profile_edit_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Edit Profile");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        init();

    }

    public void init(){
        user = myDB.getUserProfile(user_id+"");

        activity = this;

        btn_save = (Button) findViewById(R.id.user_profile_edit_save_button);
        btn_image_pciker = (ImageButton) findViewById(R.id.edit_profile_picture);

        profile_image = (ImageView) findViewById(R.id.user_profile_edit_profile_image_actionbar);

        tv_name = (EditText) findViewById(R.id.user_profile__edit_username);
        tv_email = (EditText) findViewById(R.id.user_profile_edit__email);
        tv_phone = (EditText) findViewById(R.id.user_profile_edit_phone);
        sp_countries = (Spinner) findViewById(R.id.user_edit_countries_spinner);

        tv_country_code = (TextView) findViewById(R.id.edit_profile_country_code);
        tv_fav_scripture = (EditText) findViewById(R.id.user_profile_edit_favorite_quote);

        //set the defaults from database
         tv_name.setText(user.getUser_Name());
        tv_email.setText(user.getUser_Email());
        tv_phone.setText(user.getUser_Phone());
        tv_fav_scripture.setText(user.getUser_Favorite_Scripture());

        String image_location = user.getUser_Picture();
        if(image_location!=null){
            profile_image.setImageBitmap(BitmapFactory.decodeFile(image_location));
        }

        btn_image_pciker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(activity);
            }
        });

        spinnerInit();

    }

    public void spinnerInit() {
        sp_countries.setAdapter(new MySpinnerAdapter(this, R.layout.countries_spinner, CountryDetails.country));

        String country = user.getUser_Country();
        int country_index = Arrays.asList(CountryDetails.country).indexOf(country);
        sp_countries.setSelection(country_index);

        sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int i = sp_countries.getSelectedItemPosition();
                tv_country_code.setText(CountryDetails.code[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        * handle save button
        */
        handleSave();
    }

    public void handleSave(){
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = tv_name.getText().toString();
                String email = tv_email.getText().toString();
                String fav_scripture = tv_fav_scripture.getText().toString();
                String phone = tv_country_code.getText().toString() + tv_phone.getText().toString();
                String country = sp_countries.getSelectedItem().toString();

                ContentValues values = new ContentValues();
                values.put(DeepLife.USER_FIELDS[0], name);
                values.put(DeepLife.USER_FIELDS[1], email);
                values.put(DeepLife.USER_FIELDS[2], phone);
                values.put(DeepLife.USER_FIELDS[4], country);
                values.put(DeepLife.USER_FIELDS[5], newImage);
                values.put(DeepLife.USER_FIELDS[6], fav_scripture);

                long check = myDB.update(DeepLife.Table_USER, values, user_id);
                if (check != -1) {
                    Toast.makeText(User_Profile_Edit.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }
    private void beginCrop(Uri source) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Uri destination = Uri.fromFile(new File(getExternalCacheDir(), timeStamp));
        Crop.of(source, destination).withAspect(720,720).start(this);
    }

    private void handleCrop(int resultCode, final Intent result) {
        if (resultCode == RESULT_OK) {
            ImageProcessing imageProcessing = new ImageProcessing(getApplicationContext());
            final File file = imageProcessing.createImageFile("myprofile");
            new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Looper.prepare();
                        try {
                            imageFromCrop = Glide.with(getApplicationContext()).load(Crop.getOutput(result)).asBitmap().into(720, 720).get();
                            imageFromCrop.compress(Bitmap.CompressFormat.JPEG, 70, new FileOutputStream(file.getAbsolutePath()));
                        } catch (ExecutionException e) {
                            Log.e(DeepLife.TAG, e.getMessage());
                        } catch (InterruptedException e) {
                            Log.e(DeepLife.TAG, e.getMessage());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void dummy) {
                        if (imageFromCrop != null) {
                            profile_image.setImageBitmap(imageFromCrop);
                            newImage = file.getAbsolutePath();
/*                            ContentValues values = new ContentValues();
                            values.put(DeepLife.USER_FIELDS[5], file.getAbsolutePath());
                            long check = myDB.update(DeepLife.Table_USER, values, user_id);
                            if (check != -1) {
                                profile_image.setImageBitmap(imageFromCrop);
                            }*/
                        }
                    }
                }.execute();

        } else if (resultCode == Crop.RESULT_ERROR) {
             Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

             //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
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





}
