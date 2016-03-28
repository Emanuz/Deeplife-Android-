package com.gcme.deeplife.Activities.UserProfile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.ImageProcessing.ImageProcessing;
import com.gcme.deeplife.Models.CountryDetails;
import com.gcme.deeplife.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class User_Profile_Edit extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView profile_image;
    ListView lv_schedule;
    TextView tv_build, tv_name, tv_phone, tv_gender,tv_email;
    ImageButton imageButton;
    ImageView profile_pic;
    Button btn_complet;
    Button btn_save;
    ImageButton btn_image_pciker;

    String path = "/storage/emulated/0/deeplifetest/myprofile/JPEG_20160327_193322_.jpg";

    private Spinner sp_countries;
    private ProgressDialog pDialog;
    public static String[] list;

    String user_id;
    ArrayList<String> schedule_list;

    private Bitmap theBitmap = null;

    //Database
    Database dbadapter;
    DeepLife dbhelper;


    private String mCurrentPhotoPath;
    private String newCurrentPhotoPath;
    public final static int CHANGE_PIC = 1;
    private String TAG = "Deep Life";

    Bitmap imageFromCrop = null;
    File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_page_edit);
        setSupportActionBar((Toolbar) findViewById(R.id.user_profile_edit_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.user_profile_edit_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Edit Profile");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        btn_image_pciker = (ImageButton) findViewById(R.id.edit_profile_picture);
        final Activity activity = this;
        btn_image_pciker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(activity);
            }
        });

        list =  CountryDetails.country;
        btn_save = (Button) findViewById(R.id.user_profile_edit_save_button);
        profile_image = (ImageView) findViewById(R.id.user_profile_edit_profile_image_actionbar);
        sp_countries = (Spinner) findViewById(R.id.user_countries_spinner);
        spinnerInit();

    }

    public void spinnerInit() {
        sp_countries.setAdapter(new MySpinnerAdapter(this, R.layout.countries_spinner, list));
        sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int i = sp_countries.getSelectedItemPosition();
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
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

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
            new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Looper.prepare();
                        try {
                            file = ImageProcessing.createImageFile("myprofile");
                            imageFromCrop = Glide.with(getApplicationContext()).load(Crop.getOutput(result)).asBitmap().into(720, 720).get();
                            imageFromCrop.compress(Bitmap.CompressFormat.JPEG, 70, new FileOutputStream(file.getAbsolutePath()));
                            Log.i(TAG, "Cropped image created at: " + file.getAbsolutePath());

                        } catch (ExecutionException e) {
                            Log.e(TAG, e.getMessage());
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.getMessage());
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
                            Log.i(TAG, "Image loaded");
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
