package deeplife.gcme.com.deeplife.Activities.UserProfile;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import deeplife.gcme.com.deeplife.Models.User;
import deeplife.gcme.com.deeplife.R;

public class User_Profile extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView profile_image;
    TextView tv_name,tv_email, tv_fav_scripture, tv_phone, tv_country;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_page);
        setSupportActionBar((Toolbar) findViewById(R.id.user_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        init();

    }
    public void init(){
        user = deeplife.gcme.com.deeplife.DeepLife.myDatabase.getUserProfile();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.user_profile_collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setTitle(user.getUser_Name());

        profile_image = (ImageView) findViewById(R.id.user_profile_image);

        tv_name = (TextView) findViewById(R.id.user_profile_username);
        tv_email = (TextView) findViewById(R.id.user_profile_email);
        tv_phone = (TextView) findViewById(R.id.user_profile_phone);
        tv_country = (TextView) findViewById(R.id.user_profile_country);
        tv_fav_scripture = (TextView) findViewById(R.id.user_profile_favorite_quote);
        if(deeplife.gcme.com.deeplife.DeepLife.myDatabase.get_Country_by_CountryID(user.getUser_Country()) != null){
            tv_country.setText(deeplife.gcme.com.deeplife.DeepLife.myDatabase.get_Country_by_CountryID(user.getUser_Country()).getName());
        }
        tv_name.setText(user.getUser_Name());
        tv_email.setText(user.getUser_Email());
        tv_phone.setText(user.getUser_Phone());
        tv_fav_scripture.setText(user.getUser_Favorite_Scripture());

        String image_location = user.getUser_Picture();
        if(image_location!=null){
            profile_image.setImageBitmap(BitmapFactory.decodeFile(image_location));
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

        if(id == R.id.menu_user_profile_edit){
            Intent intent = new Intent(User_Profile.this,User_Profile_Edit.class);
            startActivity(intent);
            return true;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
