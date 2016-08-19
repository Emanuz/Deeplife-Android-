package deeplife.gcme.com.deeplife.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.Helps.AppHelps;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.Main_Activity;
import deeplife.gcme.com.deeplife.R;


public class Splash extends Activity {


	@Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

				Thread splash = new Thread(){
        	@Override
        	public void run() {
        		try {
        			sleep( 1000);
        		} catch(InterruptedException e){
        		} finally {
        			getNextActivity();
        		}
        		//super.run();
        	}
        };
        
        splash.start();
	}

	public synchronized void getNextActivity() {

		int Count = deeplife.gcme.com.deeplife.DeepLife.myDatabase.count(Database.Table_USER);
		if(Count ==1){
			Intent intent = new Intent(this, Main_Activity.class);
			startActivity(intent);
			finish();
		}else{
			Intent intent = new Intent(this, AppHelps.class);
			startActivity(intent);
			finish();
		}

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
