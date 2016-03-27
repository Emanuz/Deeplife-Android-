package com.gcme.deeplife.SyncService;

import android.util.Log;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class SyncService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("JobService","The Job scheduler started");




        jobFinished(params,false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
