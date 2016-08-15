package deeplife.gcme.com.deeplife;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.FileManager.FileManager;
import deeplife.gcme.com.deeplife.Schedule.OnAlarmReceiver;
import deeplife.gcme.com.deeplife.Schedule.ReminderManager;
import deeplife.gcme.com.deeplife.SyncService.SyncService;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class DeepLife extends Application {
//    public static final String API_URL  = "http://deeplife.cccsea.org/deep_api";
    public static final String DEEP_URL  = "http://192.168.0.202/";
    public static final String API_URL  = DEEP_URL+"DeepLife_Final/public/deep_api";
    public static final String PROFILE_PIC_URL  = DEEP_URL+"DeepLife_Final/public/img/profile/";
    private static final int JOB_ID = 100;
    public static  int DOWNLOAD_STATUS = 0;
    private JobScheduler myJobScheduler;
    public static Database myDatabase;
    public static FileManager myFileManager;
    public static int Slide_Pos = 0;
    public static ReminderManager myReminderManager;
    public static Context myContext;
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";



    @Override
    public void onCreate() {
        super.onCreate();
        myReminderManager = new ReminderManager(this);
        myDatabase = new Database(this);
        myJobScheduler  = JobScheduler.getInstance(this);
        myFileManager = new FileManager(this);
        myContext = this;


        Intent intent = new Intent(this,SyncService.class);
        startService(intent);
        JobConstr();
    }
    public void JobConstr(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this,SyncService.class));
        builder.setPeriodic(10000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        myJobScheduler.schedule(builder.build());
    }


}
