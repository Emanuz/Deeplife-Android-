package com.gcme.deeplife;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.SyncService.SyncService;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class DeepLife extends Application {
    public static final String API_URL  = "http://deeplife.cccsea.org/deep_api";
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
    }
    public void JobConstr(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this,SyncService.class));
        builder.setPeriodic(3000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        myJobScheduler.schedule(builder.build());
    }
}
