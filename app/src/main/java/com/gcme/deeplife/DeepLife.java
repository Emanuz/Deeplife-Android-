package com.gcme.deeplife;

import android.app.Application;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.widget.Toast;

import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.SyncService.SyncService;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class DeepLife extends Application {
    private static final int JOB_ID = 100;
    private JobScheduler myJobScheduler;
    public static Database myDatabase;
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this,SyncService.class);
        startService(intent);
        myDatabase = new Database(this);
        myJobScheduler  = JobScheduler.getInstance(this);
        JobConstr();
        ContentValues cv = new ContentValues();
        cv.put(com.gcme.deeplife.Database.DeepLife.USER_COLUMN[1],"Fulll Name");
        cv.put(com.gcme.deeplife.Database.DeepLife.USER_COLUMN[2],"Email of user");
        cv.put(com.gcme.deeplife.Database.DeepLife.USER_COLUMN[3],"ben@beng");
        cv.put(com.gcme.deeplife.Database.DeepLife.USER_COLUMN[4],"ben");
        cv.put(com.gcme.deeplife.Database.DeepLife.USER_COLUMN[5],"Ethiopia");
        cv.put(com.gcme.deeplife.Database.DeepLife.USER_COLUMN[3], "the pic");
        if(myDatabase.count(com.gcme.deeplife.Database.DeepLife.Table_USER)<1){
            myDatabase.insert(com.gcme.deeplife.Database.DeepLife.Table_USER,cv);
            Toast.makeText(this,"Data Added to table",Toast.LENGTH_LONG).show();
        }
        ContentValues cv1 = new ContentValues();
        cv.put(com.gcme.deeplife.Database.DeepLife.LOGS_FIELDS[0],"Disciple");
        cv.put(com.gcme.deeplife.Database.DeepLife.LOGS_FIELDS[1], com.gcme.deeplife.Database.DeepLife.Sync_Tasks[0]);
        cv.put(com.gcme.deeplife.Database.DeepLife.LOGS_FIELDS[2],"12");
        long val = myDatabase.insert(com.gcme.deeplife.Database.DeepLife.Table_LOGS,cv1);
        Toast.makeText(this,"Adding Logs:-> "+val,Toast.LENGTH_LONG).show();

    }
    public void JobConstr(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this,SyncService.class));
        builder.setPeriodic(10000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        myJobScheduler.schedule(builder.build());
    }
}
