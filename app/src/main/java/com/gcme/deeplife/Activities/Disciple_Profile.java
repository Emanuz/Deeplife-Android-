package com.gcme.deeplife.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.gcme.deeplife.Activities.Build.BuildActivity;
import com.gcme.deeplife.Activities.Send.SendActivity;
import com.gcme.deeplife.Activities.Win.WinActivity;
import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.ImageProcessing.ImageProcessing;
import com.gcme.deeplife.Models.Disciples;
import com.gcme.deeplife.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Disciple_Profile extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;

    ImageView profile_image;
    ListView lv_schedule;
    TextView tv_build, tv_name, tv_phone, tv_gender, tv_email;

    ImageButton btn_changeImage;

    String disciple_id;
    Bitmap imageFromCrop = null;
    Database myDB;
    Disciples disciple;
    Activity activity;
    Button btn_complet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.disciple_profile_page);
        setSupportActionBar((Toolbar) findViewById(R.id.disciple_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDB = new Database(this);
        disciple_id = getIntent().getExtras().getString("id");
        init(disciple_id);

    }

    public void init(String disciple_id) {
        activity = this;
        disciple = myDB.getDiscipleProfile(disciple_id);

        //collapsing toolbar
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.disciple_profile_collapsing_toolbar);
        collapsingToolbarLayout.setTitle(disciple.getFull_Name());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        //get the views
        profile_image = (ImageView) findViewById(R.id.disciple_profile_image);
        btn_complet = (Button) findViewById(R.id.btn_complete_build);
        tv_build = (TextView) findViewById(R.id.profile_build_stage);
        tv_phone = (TextView) findViewById(R.id.profile_phone);
        tv_email = (TextView) findViewById(R.id.profile_email);
        tv_gender = (TextView) findViewById(R.id.profile_gender);
        lv_schedule = (ListView) findViewById(R.id.profile_schedule_list);


        btn_changeImage = (ImageButton) findViewById(R.id.disciple_btn_edit_profile_cover);
        btn_changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(activity);
            }
        });

        setData();
    }

    public void setData() {
        if (disciple.getPicture() != null) {
            profile_image.setImageBitmap(BitmapFactory.decodeFile(disciple.getPicture()));
        }
        tv_phone.setText(disciple.getPhone());
        tv_email.setText(disciple.getEmail());
        tv_gender.setText(disciple.getGender());

        setButtonListner();
    }

    public void setButtonListner(){
        final String build = disciple.getBuild_Phase();
        if(build.equals("SEND")){
            btn_complet.setVisibility(View.INVISIBLE);
        }
        btn_complet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (build.endsWith("Added")) {
                    Intent intent = new Intent(Disciple_Profile.this, WinActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("disciple_id", disciple_id);

                    if (myDB.checkExistence(DeepLife.Table_QUESTION_ANSWER, DeepLife.QUESTION_ANSWER_FIELDS[0], disciple_id, "WIN") > 0) {
                        bundle.putString("answer", "yes");
                    }

                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (build.endsWith("WIN")) {
                    Intent intent = new Intent(Disciple_Profile.this, BuildActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("disciple_id", disciple_id);
                    if (myDB.checkExistence(DeepLife.Table_QUESTION_ANSWER, DeepLife.QUESTION_ANSWER_FIELDS[0], disciple_id, "BUILD") > 0) {
                        bundle.putString("answer", "yes");
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (build.endsWith("BUILD")) {
                    Intent intent = new Intent(Disciple_Profile.this, SendActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("disciple_id", disciple_id);

                    if (myDB.checkExistence(DeepLife.Table_QUESTION_ANSWER, DeepLife.QUESTION_ANSWER_FIELDS[0], disciple_id, "SEND") > 0) {
                        bundle.putString("answer", "yes");
                    }

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.menu_about){
            Intent intent = new Intent(this,AboutDeepLife.class);
            startActivity(intent);
            return true;
        }
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
            ImageProcessing imageProcessing = new ImageProcessing(getApplicationContext());
            final File file = imageProcessing.createImage("disciples");

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
/*                        imageFromCrop = Glide.with(getApplicationContext()).load(Crop.getOutput(result)).asBitmap().into(-1, -1).get();
                        Bitmap.createScaledBitmap(imageFromCrop, 720, 720, false)
                                .compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(file.getAbsolutePath()));*/
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
                        ContentValues values = new ContentValues();
                        values.put(DeepLife.DISCIPLES_FIELDS[6], file.getAbsolutePath());

                        long check = myDB.update(DeepLife.Table_DISCIPLES, values, Integer.parseInt(disciple_id));
                        if (check != -1) {
                            profile_image.setImageBitmap(imageFromCrop);
                            Log.i(DeepLife.TAG, "Image successfully changed \n New Image location: " + disciple.getPicture());
//                            myDB.insert(DeepLife.Table_LOGS,);
                        }
                    }
                }
            }.execute();

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.dispose();
    }
}