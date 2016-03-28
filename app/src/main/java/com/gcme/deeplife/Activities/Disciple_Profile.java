package com.gcme.deeplife.Activities;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.ImageProcessing.ImageProcessing;
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

public class Disciple_Profile extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView profile_image;
    ListView lv_schedule;
    TextView tv_build, tv_name, tv_phone, tv_gender,tv_email;
    ImageButton imageButton;
    ImageView profile_pic;
    Button btn_complet;
    ImageButton btn_changeImage;

    String disciple_id;
    ArrayList<String> schedule_list;
    Database dbadapter;
    DeepLife dbhelper;

    private Bitmap theBitmap = null;

    private String mCurrentPhotoPath;
    private String newCurrentPhotoPath;
    public final static int CHANGE_PIC = 1;
    private String TAG = "Deep Life";

    Bitmap imageFromCrop = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.disciple_profile_page);
        setSupportActionBar((Toolbar) findViewById(R.id.disciple_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.disciple_profile_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Roger Mulugeta");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        profile_image = (ImageView) findViewById(R.id.disciple_profile_image);
        btn_changeImage = (ImageButton) findViewById(R.id.disciple_btn_edit_profile_cover);
        final Activity activity = this;
        btn_changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(activity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);
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
        Crop.of(source, destination).withAspect(720, 720).start(this);
    }

    private void handleCrop(int resultCode, final Intent result) {
        if (resultCode == RESULT_OK) {
            new AsyncTask<Void, Void, Void>() {
                final File file = ImageProcessing.createImageFile("disciples");
                @Override
                protected Void doInBackground(Void... params) {
                    Looper.prepare();
                    try {
                        imageFromCrop = Glide.with(getApplicationContext()).load(Crop.getOutput(result)).asBitmap().into(-1, -1).get();
                        Bitmap.createScaledBitmap(imageFromCrop,800,850,false)
                                .compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(file.getAbsolutePath()));
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


}
