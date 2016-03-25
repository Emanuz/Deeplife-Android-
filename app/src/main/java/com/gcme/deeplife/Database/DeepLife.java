package com.gcme.deeplife.Database;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;


public class DeepLife extends Application {

    public static final String Table_DISCIPLES = "DISCIPLES";
    public static final String Table_SCHEDULES = "SCHEDULES";
    public static final String Table_LOGS = "LOGS";
    public static final String Table_USER = "USER";
    public static final String Table_Reports = "Reports";
    public static final String Table_QUESTION_LIST = "QUESTION_LIST";
    public static final String Table_QUESTION_ANSWER = "QUESTION_ANSWER";



    public static final String[] DISCIPLES_FIELDS = { "Full_Name", "Email", "Phone", "Country","Build_phase","Gender","Picture" };

    public static final String[] LOGS_FIELDS = { "Type", "Loc_ID" };


    public static final String[] SCHEDULES_FIELDS = { "Dis_Phone", "Alarm_Time","Alarm_Repeat","Description" };
    public static final String[] USER_FIELDS = { "Full_Name", "Email","Phone","Password","Country","Picture" };
    public static final String[] QUESTION_LIST_FIELDS = {"Category","Description", "Note","Mandatory"};
    public static final String[] REPORTS_FIELDS = {"Report_Date","Qst1","Qst2", "Qst3","Qst4","Qst5","Qst6","Qst7","Qst8","Qst9","Qst10","Qst11","Qst12"};

    public static final String[] QUESTION_ANSWER_FIELDS = {"Disciple_ID","Question_ID", "Answer","Build_Stage"};

    public static final String[] DISCIPLES_COLUMN = { "id", "Full_Name","Email", "Phone", "Country","Build_phase","Gender","Picture" };
    public static final String[] SCHEDULES_COLUMN = { "id","Dis_Phone", "Alarm_Time","Alarm_Repeat","Description" };
    public static final String[] REPORTS_COLUMN = {"id","Report_Date","Qst1","Qst2", "Qst3","Qst4","Qst5","Qst6","Qst7","Qst8","Qst9","Qst10","Qst11","Qst12"};
    public static final String[] LOGS_COLUMN = { "id", "Type", "Loc_ID" };
    public static final String[] USER_COLUMN = { "id", "Full_Name", "Email","Phone","Password","Country","Picture" };
    public static final String[] QUESTION_LIST_COLUMN = {"id","Category","Description", "Note","Mandatory"};
    public static final String[] QUESTION_ANSWER_COLUMN = {"id", "Disciple_ID", "Question_ID", "Answer","Build_Stage"};


    public static Database myDatabase;
    public static Context myContext;
    public static Intent AlarmIntent;
    public static PendingIntent AlarmPendingIntent;
    public static AlarmManager am;

    public static void Set_Alarm(Calendar calendar){
        am.set(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmPendingIntent);
        // am.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 1000*30, DeepLife.AlarmPendingIntent);
    }
    public static void Cancel_Alarm(){
        am.cancel(AlarmPendingIntent);
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        myContext = this;

        myDatabase = new Database(getApplicationContext());

/*        Intent intent = new Intent(this, Service.class);
        if(myDatabase.count(Table_USER)==1){
            startService(intent);
        }
        AlarmIntent = new Intent(this, Alarm_BroadCast.class);*/


        AlarmPendingIntent = PendingIntent.getBroadcast(this, 0, AlarmIntent, 0);
        am = (AlarmManager) myContext.getSystemService(Context.ALARM_SERVICE);
    }
    //Register User Profile
    public static void Register_Profile(JSONArray NOTIS) throws JSONException {
        myDatabase.Delete_All(Table_USER);
        if (NOTIS.length() > 0) {
            for (int i = 0; i < NOTIS.length(); i++) {
                JSONObject obj = NOTIS.getJSONObject(i);
                ContentValues sch_vals = new ContentValues();
                sch_vals.put(USER_FIELDS[0], obj.getString(USER_FIELDS[0]));
                sch_vals.put(USER_FIELDS[1], obj.getString(USER_FIELDS[1]));
                sch_vals.put(USER_FIELDS[2], obj.getString(USER_FIELDS[2]));
                sch_vals.put(USER_FIELDS[3], obj.getString(USER_FIELDS[3]));
                sch_vals.put(USER_FIELDS[4], obj.getString(USER_FIELDS[4]));
                sch_vals.put(USER_FIELDS[5], obj.getString(USER_FIELDS[4]));
                myDatabase.insert(Table_USER, sch_vals);
                Log.i("Sync_Service", "Registering User_Profile to Phone DataBase:");
                Log.i("Sync_Service", "User_Full_Name --> " + obj.getString(USER_FIELDS[0]));
            }
        }
    }
    public static void Register_disciple(JSONArray NOTIS) throws JSONException {
        if (NOTIS.length() > 0) {
            for (int i = 0; i < NOTIS.length(); i++) {
                JSONObject obj = NOTIS.getJSONObject(i);
                ContentValues sch_vals = new ContentValues();
                sch_vals.put(DISCIPLES_FIELDS[0], obj.getString(DISCIPLES_FIELDS[0]));
                sch_vals.put(DISCIPLES_FIELDS[1], obj.getString(DISCIPLES_FIELDS[1]));
                sch_vals.put(DISCIPLES_FIELDS[2], obj.getString(DISCIPLES_FIELDS[2]));
                sch_vals.put(DISCIPLES_FIELDS[3], obj.getString(DISCIPLES_FIELDS[3]));
                sch_vals.put(DISCIPLES_FIELDS[4], obj.getString(DISCIPLES_FIELDS[4]));
                sch_vals.put(DISCIPLES_FIELDS[5], obj.getString(DISCIPLES_FIELDS[5]));
                sch_vals.put(DISCIPLES_FIELDS[6], obj.getString(DISCIPLES_FIELDS[6]));
                myDatabase.insert(Table_DISCIPLES, sch_vals);
                Log.i("Sync_Service", "Registering Disciple to Phone DataBase:");
                Log.i("Sync_Service", "Full Name--> " + obj.getString(DISCIPLES_FIELDS[0]));
            }
        }
    }
    public static void Register_Schedule(JSONArray NOTIS) throws JSONException {
        if (NOTIS.length() > 0) {
            for (int i = 0; i < NOTIS.length(); i++) {
                JSONObject obj = NOTIS.getJSONObject(i);
                ContentValues sch_vals = new ContentValues();
                sch_vals.put(SCHEDULES_FIELDS[0], obj.getString(SCHEDULES_FIELDS[0]));
                sch_vals.put(SCHEDULES_FIELDS[1], obj.getString(SCHEDULES_FIELDS[1]));
                sch_vals.put(SCHEDULES_FIELDS[2], obj.getString(SCHEDULES_FIELDS[2]));
                sch_vals.put(SCHEDULES_FIELDS[3], obj.getString(SCHEDULES_FIELDS[3]));
                myDatabase.insert(Table_SCHEDULES, sch_vals);
                Log.i("Sync_Service", "Registering Schedule to Phone DataBase:");
                Log.i("Sync_Service", "Disciple Phone --> " + obj.getString(SCHEDULES_FIELDS[0]));
            }
        }
    }
    public static void Register_Question(JSONArray NOTIS) throws JSONException {
        if (NOTIS.length() > 0) {
            for (int i = 0; i < NOTIS.length(); i++) {
                JSONObject obj = NOTIS.getJSONObject(i);
                ContentValues sch_vals = new ContentValues();
                sch_vals.put(QUESTION_LIST_FIELDS[0], obj.getString(QUESTION_LIST_FIELDS[0]));
                sch_vals.put(QUESTION_LIST_FIELDS[1], obj.getString(QUESTION_LIST_FIELDS[1]));
                sch_vals.put(QUESTION_LIST_FIELDS[2], obj.getString(QUESTION_LIST_FIELDS[2]));
                sch_vals.put(QUESTION_LIST_FIELDS[3], obj.getString(QUESTION_LIST_FIELDS[3]));
                myDatabase.insert(Table_QUESTION_LIST, sch_vals);
                Log.i("Sync_Service", "Registering Question to Phone DataBase:");
                Log.i("Sync_Service", "Question category --> " + obj.getString(QUESTION_LIST_FIELDS[0]));
            }
        }
    }
    public static boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

}
