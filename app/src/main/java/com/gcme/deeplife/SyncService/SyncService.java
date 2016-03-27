package com.gcme.deeplife.SyncService;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.DeepLife;
import com.gcme.deeplife.Models.Logs;
import com.gcme.deeplife.Models.Question;
import com.gcme.deeplife.Models.User;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class SyncService extends JobService {
    private static final String TAG = "SyncService";
    private Database myDatabase;
    private List<Object> Param;
    private Gson myParser;
    private List<kotlin.Pair<String,String>> Send_Param;
    private User user;
    public SyncService(){
        Param = new ArrayList<Object>();
        myParser = new Gson();
        user = DeepLife.myDatabase.getUser();
    }
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("JobService", "The Job scheduler started");
        Param.add(DeepLife.myDatabase.getUser());
        Send_Param = new ArrayList<kotlin.Pair<String,String>>();
        Send_Param.add(new kotlin.Pair<String, String>("User_Name",user.getUser_Name()));
        Send_Param.add(new kotlin.Pair<String, String>("User_Pass",user.getUser_Pass()));
        Send_Param.add(new kotlin.Pair<String, String>("Service",getService()));
        Send_Param.add(new kotlin.Pair<String, String>("Param",myParser.toJson(getParam())));


        Fuel.post("http://192.168.0.40/SyncSMS/public/deep_api", Send_Param).responseString(new Handler<String>() {
            @Override
            public void success(Request request, Response response, String s) {
                Log.i(TAG, "Request: \n" + request);
                Gson myGson = new Gson();
                Log.i(TAG, "Response: \n" + s);
            }

            @Override
            public void failure(Request request, Response response, FuelError fuelError) {
                Log.i(TAG, "Error: \n" + fuelError);
            }
        });
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public String getService(){
        if(DeepLife.myDatabase.getSendLogs().size()>0){
            return "Send_Log";
        }
        return "";
    }
    public ArrayList<Object> getParam(){
        ArrayList<Object> Found = new ArrayList<Object>();
        ArrayList<Logs> foundData = DeepLife.myDatabase.getSendLogs();
        if(foundData.size()>0){
            for(int i=0;i<foundData.size();i++){
                Found.add(foundData.get(i));
            }
        }
        return Found;
    }
}
