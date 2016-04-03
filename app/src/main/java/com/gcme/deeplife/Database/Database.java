
package com.gcme.deeplife.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gcme.deeplife.Models.Disciples;
import com.gcme.deeplife.Models.Logs;
import com.gcme.deeplife.Models.Question;
import com.gcme.deeplife.Models.QuestionAnswer;
import com.gcme.deeplife.Models.Schedule;
import com.gcme.deeplife.Models.User;
import com.gcme.deeplife.SyncService.SyncService;

import java.util.ArrayList;

public class Database {

    private static Database sInstance;
	private SQLiteDatabase myDatabase;
	private SQL_Helper mySQL;
	private Context myContext;
    private static final String TAG = "Database";

    public Database(Context context){
        myContext = context;
        mySQL = new SQL_Helper(myContext);
        myDatabase = mySQL.getWritableDatabase();
        mySQL.createTables(DeepLife.Table_DISCIPLES, DeepLife.DISCIPLES_FIELDS);
        mySQL.createTables(DeepLife.Table_LOGS, DeepLife.LOGS_FIELDS);
        mySQL.createTables(DeepLife.Table_USER, DeepLife.USER_FIELDS);
        mySQL.createTables(DeepLife.Table_SCHEDULES, DeepLife.SCHEDULES_FIELDS);
        mySQL.createTables(DeepLife.Table_QUESTION_LIST,DeepLife.QUESTION_LIST_FIELDS);
        mySQL.createTables(DeepLife.Table_QUESTION_ANSWER, DeepLife.QUESTION_ANSWER_FIELDS);
        mySQL.createTables(DeepLife.Table_Reports, DeepLife.REPORT_FIELDS);
        mySQL.createTables(DeepLife.Table_Report_Forms, DeepLife.REPORT_FORM_FIELDS);
    }

    public void dispose(){
        myDatabase.close();
    }

    public long insert(String DB_Table,ContentValues cv){
        Log.i(TAG, "Inserting New Data on: "+DB_Table);
        long state = myDatabase.insert(DB_Table, null, cv);
        Log.i(TAG, "Inserting->: "+cv.toString());
        Log.i(TAG, "Inserting Completed->: "+state+"\n");
        return state;
    }
    public long Delete_All(String DB_Table){
        long state = myDatabase.delete(DB_Table, null, null);
        return state;
    }
    public long remove(String DB_Table,int id){
        String[] args = {""+id};
        long val = myDatabase.delete(DB_Table, "id = ?", args);
        return val;
    }

    public long update(String DB_Table,ContentValues cv,int id){
        Log.i(TAG, "Updating Table: "+DB_Table);
        String[] args = {""+id};
        long state = myDatabase.update(DB_Table, cv, "id = ?", args);
        Log.i(TAG, "Updating Data: "+cv.toString());
        Log.i(TAG, "Updating Completed: "+state+"\n");
        return state;
    }

    public int count(String DB_Table){
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        if(c != null){
            return c.getCount();
        }else{
            return 0;
        }
    }
    public Cursor getAll(String DB_Table){
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        return c;
    }

    public String get_Value_At_Top(String DB_Table,String column){
        String str = "";
        try {
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            str = c.getString(c.getColumnIndex(column));
        }catch (Exception e){

        }

        return str;
    }

    public String get_Value_At_Bottom(String DB_Table,String column){
        String str = "";
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        c.moveToLast();
        str = c.getString(c.getColumnIndex(column));
        return str;
    }

    public Cursor get_value_by_ID (String DB_Table,String id){
        Cursor cur = myDatabase.rawQuery("select * from " + DB_Table + " where id=" + id, null);
        return cur;
    }

    public String get_Name_by_phone(String phone){
        String name = "";
        String DB_Table = DeepLife.Table_DISCIPLES;
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), DeepLife.DISCIPLES_FIELDS[2]+" = '"+phone+"'", null, null, null, null);
        c.moveToLast();
        name = c.getString(c.getColumnIndex(DeepLife.DISCIPLES_FIELDS[0]));
        return name;
    }
    public long Delete_By_ID(String DB_Table,int pos){
        String[] args = {""+pos};
        long val = myDatabase.delete(DB_Table, "id = ?", args);
        return val;
    }
    public long Delete_By_Column(String DB_Table,String column,String val){
        String[] args = {val};
        long v = myDatabase.delete(DB_Table, column + " = ?", args);
        return v;
    }
    public void deleteTop(String DB_Table){
        String column = getColumns(DB_Table)[0];
        String value = get_Value_At_Top(DB_Table, column);
        Delete_By_Column(DB_Table, column, value);
    }
    public int get_Top_ID(String DB_Table){
        int pos = -1;
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        c.moveToFirst();
        pos = Integer.valueOf(c.getString(c.getColumnIndex("id")));
        return pos;
    }


    public ArrayList<Question> get_All_Questions(String Category){
        String DB_Table = DeepLife.Table_QUESTION_LIST;
        ArrayList<Question> found = new ArrayList<Question>();
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), DeepLife.QUESTION_LIST_FIELDS[0]+" = '"+Category+"'", null, null, null, null);
        c.moveToFirst();

        for(int i=0;i<c.getCount();i++){
            c.moveToPosition(i);
            Question dis = new Question();
            dis.setId(c.getString(c.getColumnIndex(DeepLife.QUESTION_LIST_COLUMN[0])));
            dis.setCategory(c.getString(c.getColumnIndex(DeepLife.QUESTION_LIST_COLUMN[1])));
            dis.setDescription(c.getString(c.getColumnIndex(DeepLife.QUESTION_LIST_COLUMN[2])));
            dis.setNote(c.getString(c.getColumnIndex(DeepLife.QUESTION_LIST_COLUMN[3])));
            dis.setMandatory(c.getString(c.getColumnIndex(DeepLife.QUESTION_LIST_COLUMN[4])));
            found.add(dis);
        }
        return found;
    }


    public ArrayList<String> get_all_in_column(String DB_Table,String atColumn){
        ArrayList<String> found = new ArrayList<String>();
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        c.moveToFirst();
        for(int i = 0;i < c.getCount();i++){
            c.moveToPosition(i);
            String DB_Val = c.getString(c.getColumnIndex(atColumn));
            if(!found.contains(DB_Val)){
                found.add(DB_Val);
            }
        }
        return found;
    }
    public String get_DiscipleName(String phone){
        String Name = null;
        String DB_Table = DeepLife.Table_DISCIPLES;
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            c.moveToPosition(i);
            String str = c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[3]));
            if(str.equals(phone)){
                Name = c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[1]));
            }
        }
        return Name;
    }
    public int get_LogID(String Task,String Value){
        Log.i(TAG, "Get LogID:->");
		int id = 0;
		String DB_Table = DeepLife.Table_LOGS;
		Cursor c = myDatabase.query(DB_Table, DeepLife.LOGS_COLUMN, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                String task = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[2]));
                String value = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[3]));
                Log.i(TAG, "Get LogID: " + task + " --- " + value);
                if(Task.equals(task) && Value.equals(value)){
                    String _id = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[0]));
                    id = Integer.valueOf(_id);
                }
            }
        }

		return id;
	}
    public ArrayList<Disciples> getDisciples(){
        Log.i(TAG, "GetDisciples: \n");
        String DB_Table = DeepLife.Table_DISCIPLES;
        ArrayList<Disciples> found = new ArrayList<Disciples>();
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, DeepLife.DISCIPLES_COLUMN[0] + " DESC");
        if (c.getCount() > 0) {
            c.moveToFirst();
            for(int i=0;i<c.getCount(); i++) {

                c.moveToPosition(i);
                Disciples dis = new Disciples();
                dis.setId(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[0])));
                dis.setFull_Name(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[1])));
                dis.setEmail(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[2])));
                dis.setPhone(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[3])));
                dis.setCountry(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[4])));
                dis.setBuild_Phase(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[5])));
                dis.setGender(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[6])));
                dis.setPicture(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[7])));
                Log.i(TAG, "Found Disciples:-> "+dis.toString());
                found.add(dis);
            }
        }

        return found;
    }

     public ArrayList<Schedule> get_All_Schedule(){
         Log.i(TAG, "GetAll Schedule:\n");
        String DB_Table = DeepLife.Table_SCHEDULES;
        ArrayList<Schedule> found = new ArrayList<Schedule>();
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
         if (c.getCount() > 0) {
             c.moveToFirst();
             for(int i=0;i<c.getCount();i++){
                 c.moveToPosition(i);
                 Schedule dis = new Schedule();
                 dis.setID(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[0])));
                 dis.setUser_Id(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[1])));
                 dis.setTitle(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[2])));
                 dis.setAlarm_Time(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[3])));
                 dis.setAlarm_Repeat(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[4])));
                 dis.setDescription(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[5])));
                 Log.i(TAG, "Found Schedules:-> "+dis.toString());
                 found.add(dis);
             }
         }
        return found;
    }

    public ArrayList<Schedule> get_Schedule_With_User(String Dis_ID){
        String DB_Table = DeepLife.Table_SCHEDULES;
        ArrayList<Schedule> found = new ArrayList<Schedule>();
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++){
            c.moveToPosition(i);
            Schedule dis = new Schedule();
            String id = c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[1]));
            if(Dis_ID.equals(id)){
                dis.setID(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[0])));
                dis.setUser_Id(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[1])));
                dis.setTitle(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[2])));
                dis.setAlarm_Time(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[3])));
                dis.setAlarm_Repeat(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[4])));
                dis.setDescription(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[5])));
                Log.i(TAG, "Found Schedules:->:" + dis.toString());
                found.add(dis);
            }
        }
        return found;
    }



    public Schedule getScheduleWithId(String id){

        Schedule dis = new Schedule();
        String DB_Table = DeepLife.Table_SCHEDULES;
        Cursor c = myDatabase.rawQuery("select * from " + DB_Table + " where id=" + id, null);
        c.moveToFirst();
        if(c.getCount()>0){
            dis.setID(id);
            dis.setUser_Id(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[1])));
            dis.setTitle(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[2])));
            dis.setTitle(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[2])));
            dis.setAlarm_Time(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[3])));
            dis.setAlarm_Repeat(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[4])));
            dis.setDescription(c.getString(c.getColumnIndex(DeepLife.SCHEDULES_COLUMN[5])));
            return dis;
        }
        return null;
    }

    public Disciples getDiscipleProfile(String Dis_ID){
        Log.i(TAG, "GetDiscipleProfile");
        Disciples dis = new Disciples();
        String DB_Table = DeepLife.Table_DISCIPLES;
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
            for(int i=0; i<c.getCount();i++){
                c.moveToPosition(i);
                String ID = c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[0]));
                Log.i(TAG, "GetDiscipleProfile Comparing: "+ID+" | "+Dis_ID);
                if(ID.equals(Dis_ID)){
                    dis.setId(Dis_ID);
                    dis.setFull_Name(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[1])));
                    dis.setEmail(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[2])));
                    dis.setPhone(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[3])));
                    dis.setCountry(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[4])));
                    dis.setBuild_Phase(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[5])));
                    dis.setGender(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[6])));
                    dis.setPicture(c.getString(c.getColumnIndex(DeepLife.DISCIPLES_COLUMN[7])));
                    Log.i(TAG, "Found DiscipleProfile:-> " + dis.toString());
                    return dis;
                }
            }
        }
        return null;
    }


    public User getUserProfile(String user_ID){

        User dis = new User();
        String DB_Table = DeepLife.Table_USER;
        Cursor c = myDatabase.rawQuery("select * from " + DB_Table + " where id=" + user_ID, null);
        c.moveToFirst();
        if(c.getCount()>0){
            dis.setId(user_ID);
            dis.setUser_Name(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[1])));
            dis.setUser_Email(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[2])));
            dis.setUser_Phone(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[3])));
            dis.setUser_Pass(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[4])));
            dis.setUser_Country(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[5])));
            dis.setUser_Picture(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[6])));
            dis.setUser_Favorite_Scripture(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[7])));

            return dis;
        }
        return null;
    }


    public long checkExistence(String Db_Table,String column,String id, String build){

        Cursor cursor = myDatabase.query(Db_Table, getColumns(Db_Table),column+" = '"+id+"' and "+DeepLife.QUESTION_ANSWER_FIELDS[3]+" = '"+ build+"'",null,null,null,null);
        return cursor.getCount();

    }
    public int count_Questions(String DB_Table, String Category){
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), DeepLife.QUESTION_LIST_FIELDS[0]+" = '"+Category+"'", null, null, null, null);
        return c.getCount();
    }

    public ArrayList<QuestionAnswer> get_Answer(String Dis_ID, String phase){
        String DB_Table = DeepLife.Table_QUESTION_ANSWER;
        ArrayList<QuestionAnswer> found = new ArrayList<QuestionAnswer>();
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), DeepLife.QUESTION_ANSWER_FIELDS[0] + " = '" + Dis_ID + "' and " + DeepLife.QUESTION_ANSWER_FIELDS[3] + "= '" + phase + "'", null, null, null, null);
        Log.i("Deep Life", "Answer from db count: " + c.getCount() + " data: " + c.toString());
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            c.moveToPosition(i);
            QuestionAnswer dis = new QuestionAnswer();
                dis.setId(c.getString(c.getColumnIndex(DeepLife.QUESTION_ANSWER_COLUMN[0])));
                dis.setDisciple_id(c.getString(c.getColumnIndex(DeepLife.QUESTION_ANSWER_COLUMN[1])));
                dis.setQuestion_id(c.getString(c.getColumnIndex(DeepLife.QUESTION_ANSWER_COLUMN[2])));
                dis.setAnswer(c.getString(c.getColumnIndex(DeepLife.QUESTION_ANSWER_COLUMN[3])));
                dis.setBuild_Phase(c.getString(c.getColumnIndex(DeepLife.QUESTION_ANSWER_COLUMN[4])));

            found.add(dis);

        }
        return found;
    }
    public User getUser(){
        int pos = -1;
        Cursor c = myDatabase.query(DeepLife.Table_USER, DeepLife.USER_COLUMN, null, null, null, null, null);
        User newUser = new User();
        if(c != null && c.getCount()>0){
            c.moveToFirst();
            newUser.setId(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[0])));
            newUser.setUser_Name(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[3])));
            newUser.setUser_Pass(c.getString(c.getColumnIndex(DeepLife.USER_COLUMN[4])));
        }
        return newUser;
    }
    public ArrayList<Logs> getSendLogs(){
        Log.i(TAG, "SendLogs:\n");
        ArrayList<Logs> Found = new ArrayList<>();
        Cursor c = myDatabase.query(DeepLife.Table_LOGS, DeepLife.LOGS_COLUMN, null, null, null, null, null);
        if(c != null && c.getCount()>0){
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                String str = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[2]));
                Log.i(TAG, "Comparing-> \n" + SyncService.Sync_Tasks[0] + " | "+str);
                if (SyncService.Sync_Tasks[0].equals(str)){
                    Log.i(TAG, "SendLogs Count:-> " + c.getCount());
                    Logs newLogs = new Logs();
                    newLogs.setId(c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[0])));
                    newLogs.setType(c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[1])));
                    newLogs.setTask(c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[2])));
                    newLogs.setValue(c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[3])));
                    Log.i(TAG, "Found for SendLogs:-> \n" + newLogs.toString());
                    Found.add(newLogs);
                }
            }
        }
        return Found;
    }
    public ArrayList<Disciples> getSendDisciples(){
        Log.i(TAG, "SendDisciples:\n");
        ArrayList<Disciples> Found = new ArrayList<>();
        Cursor c = myDatabase.query(DeepLife.Table_LOGS, DeepLife.LOGS_COLUMN, null, null, null, null, null);
        if(c != null && c.getCount()>0){
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                String str = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[2]));
                String id = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[3]));
                String ID = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[0]));
                Log.i(TAG, "Comparing-> \n" + SyncService.Sync_Tasks[1] + " | "+str);
                if(SyncService.Sync_Tasks[1].equals(str)){
                    Log.i(TAG, "SendDisciples Count:-> " + c.getCount());
                    Disciples newDisciples = getDiscipleProfile(id);
                    if(newDisciples !=null){
                        newDisciples.setId(ID);
                        Log.i(TAG, "Found for Send:-> \n" + newDisciples.toString());
                        Found.add(newDisciples);
                    }
                }
            }
        }
        return Found;
    }
    public ArrayList<Disciples> getUpdateDisciples(){
        Log.i(TAG, "UpdateDisciples:\n");
        ArrayList<Disciples> Found = new ArrayList<>();
        Cursor c = myDatabase.query(DeepLife.Table_LOGS, DeepLife.LOGS_COLUMN, null, null, null, null, null);
        if(c != null && c.getCount()>0){
            c.moveToFirst();
            Log.i(TAG, "UpdateDisciples Count:-> " + c.getCount());
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                String ID = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[0]));
                String str = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[2]));
                String id = c.getString(c.getColumnIndex(DeepLife.LOGS_COLUMN[3]));
                Log.i(TAG, "Comparing-> \n" + SyncService.Sync_Tasks[3] + " | "+str);
                if(SyncService.Sync_Tasks[3].equals(str)){
                    Disciples newDisciples = getDiscipleProfile(id);
                    if(newDisciples !=null){
                        newDisciples.setId(ID);
                        Found.add(newDisciples);
                    }
                }
            }
        }
        return Found;
    }



    private String[] getColumns(String DB_Table){
        String[] strs = null;
        if(DB_Table == DeepLife.Table_DISCIPLES){
            strs = DeepLife.DISCIPLES_COLUMN;
        }else if(DB_Table == DeepLife.Table_LOGS){
            strs = DeepLife.LOGS_COLUMN;
        }else if(DB_Table == DeepLife.Table_USER){
            strs = DeepLife.USER_COLUMN;
        }else if(DB_Table == DeepLife.Table_SCHEDULES){
            strs = DeepLife.SCHEDULES_COLUMN;
        } else if(DB_Table == DeepLife.Table_QUESTION_LIST){
            strs = DeepLife.QUESTION_LIST_COLUMN;
        } else if(DB_Table == DeepLife.Table_QUESTION_ANSWER){
            strs = DeepLife.QUESTION_ANSWER_COLUMN;
        } else if(DB_Table == DeepLife.Table_Reports){
            strs = DeepLife.REPORT_COLUMN;
        }else if(DB_Table == DeepLife.Table_Report_Forms){
            strs = DeepLife.REPORT_FORM_COLUMN;
        }
        return strs;
    }
}
