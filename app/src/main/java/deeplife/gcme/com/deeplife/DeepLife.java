package deeplife.gcme.com.deeplife;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.FileManager.FileManager;
import deeplife.gcme.com.deeplife.SyncService.SyncService;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class DeepLife extends Application {
 //   public static final String API_URL  = "http://deeplife.cccsea.org/deep_api";

    public static final String API_URL  = "http://192.168.0.53/DeepLife-Web-API/public/deep_api";
    private static final int JOB_ID = 100;
    public static  int DOWNLOAD_STATUS = 0;
    private JobScheduler myJobScheduler;
    public static Database myDatabase;
    public static FileManager myFileManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this,SyncService.class);
        startService(intent);
        myDatabase = new Database(this);
        myJobScheduler  = JobScheduler.getInstance(this);
        myFileManager = new FileManager(this);
        JobConstr();
    }

    public void JobConstr(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this,SyncService.class));
        builder.setPeriodic(10000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        myJobScheduler.schedule(builder.build());
    }
}
