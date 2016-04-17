
package deeplife.gcme.com.deeplife.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import deeplife.gcme.com.deeplife.Models.Country;
import deeplife.gcme.com.deeplife.Models.Disciples;
import deeplife.gcme.com.deeplife.Models.Logs;
import deeplife.gcme.com.deeplife.Models.NewsFeed;
import deeplife.gcme.com.deeplife.Models.Question;
import deeplife.gcme.com.deeplife.Models.QuestionAnswer;
import deeplife.gcme.com.deeplife.Models.ReportItem;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.Models.User;
import deeplife.gcme.com.deeplife.SyncService.SyncService;

import java.security.spec.ECField;
import java.util.ArrayList;

public class Database {
    public static final String Table_DISCIPLES = "DISCIPLES";
    public static final String Table_SCHEDULES = "SCHEDULES";
    public static final String Table_LOGS = "LOGS";
    public static final String Table_USER = "USER";
    public static final String Table_NEWSFEED = "NewsFeedf";
    public static final String Table_COUNTRY = "COUNTRIES";
    public static final String Table_Reports = "Reports";
    public static final String Table_Report_Forms = "Report_Form";
    public static final String Table_QUESTION_LIST = "QUESTION_LIST";
    public static final String Table_QUESTION_ANSWER = "QUESTION_ANSWER";



    public static final String[] DISCIPLES_FIELDS = {"Full_Name", "Email", "Phone", "Country","Build_phase","Gender","Picture" };
    public static final String[] LOGS_FIELDS = { "Type", "Task","Value" };
    public static final String[] NewsFeed_FIELDS = { "News_ID", "Title","Content","ImageURL","ImagePath","PubDate","Category" };
    public static final String[] COUNTRY_FIELDS = { "Country_id", "iso3","name","code" };
    public static final String[] SCHEDULES_FIELDS = { "Disciple_Phone","Title","Alarm_Time","Alarm_Repeat","Description"};
    public static final String[] USER_FIELDS = { "Full_Name", "Email","Phone","Password","Country","Picture","Favorite_Scripture" };
    public static final String[] QUESTION_LIST_FIELDS = {"Category","Description", "Note","Mandatory"};
    public static final String[] REPORT_FORM_FIELDS = {"Report_ID","Category","Questions"};
    public static final String[] REPORT_FIELDS = {"Report_ID","Value","Date"};
    public static final String[] QUESTION_ANSWER_FIELDS = {"Disciple_Phone","Question_ID", "Answer","Build_Stage"};

    public static final String[] DISCIPLES_COLUMN = { "id", "Full_Name","Email", "Phone", "Country","Build_phase","Gender","Picture" };
    public static final String[] SCHEDULES_COLUMN = { "id","Disciple_Phone","Title","Alarm_Time","Alarm_Repeat","Description" };
    public static final String[] REPORT_FORM_COLUMN = {"id","Report_ID","Category","Questions"};
    public static final String[] NewsFeed_COLUMN = { "id","News_ID", "Title","Content","ImageURL","ImagePath","PubDate","Category" };
    public static final String[] REPORT_COLUMN = {"id","Report_ID","Value","Date"};
    public static final String[] COUNTRY_COLUMN = {"id", "Country_id", "iso3","name","code" };
    public static final String[] LOGS_COLUMN = { "id", "Type", "Task","Value" };
    public static final String[] USER_COLUMN = { "id", "Full_Name", "Email","Phone","Password","Country","Picture","Favorite_Scripture" };
    public static final String[] QUESTION_LIST_COLUMN = {"id","Category","Description", "Note","Mandatory"};
    public static final String[] QUESTION_ANSWER_COLUMN = {"id", "Disciple_Phone","Question_ID", "Answer","Build_Stage"};


	private SQLiteDatabase myDatabase;
	private SQL_Helper mySQL;
	private Context myContext;
    public static final String TAG = "Database";

    public Database(Context context){
        myContext = context;
        mySQL = new SQL_Helper(myContext);
        myDatabase = mySQL.getWritableDatabase();
        mySQL.createTables(Table_DISCIPLES, DISCIPLES_FIELDS);
        mySQL.createTables(Table_LOGS, LOGS_FIELDS);
        mySQL.createTables(Table_USER, USER_FIELDS);
        mySQL.createTables(Table_SCHEDULES, SCHEDULES_FIELDS);
        mySQL.createTables(Table_QUESTION_LIST,QUESTION_LIST_FIELDS);
        mySQL.createTables(Table_QUESTION_ANSWER, QUESTION_ANSWER_FIELDS);
        mySQL.createTables(Table_Reports, REPORT_FIELDS);
        mySQL.createTables(Table_Report_Forms, REPORT_FORM_FIELDS);
        mySQL.createTables(Table_COUNTRY, COUNTRY_FIELDS);
        mySQL.createTables(Table_NEWSFEED, NewsFeed_FIELDS);
    }


    public long insert(String DB_Table,ContentValues cv){
        long state = myDatabase.insert(DB_Table, null, cv);
        Log.i(TAG, "Inserting->: " + cv.toString());
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
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToLast();
            str = c.getString(c.getColumnIndex(column));
        }catch (Exception e){

        }
        return str;
    }

    public Cursor get_value_by_ID (String DB_Table,String id){
        Cursor cur = myDatabase.rawQuery("select * from " + DB_Table + " where id=" + id, null);
        return cur;
    }

    public String get_Name_by_phone(String phone){
        String name = "";
        try{
            String DB_Table = Table_DISCIPLES;
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), DISCIPLES_FIELDS[2] + " = '" + phone + "'", null, null, null, null);
            c.moveToLast();
            name = c.getString(c.getColumnIndex(DISCIPLES_FIELDS[0]));
        }catch (Exception e){

        }
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
    public int get_Top_ID(String DB_Table){
        int pos = -1;
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            pos = Integer.valueOf(c.getString(c.getColumnIndex("id")));
        }catch (Exception e){

        }
        return pos;
    }


    public ArrayList<Question> get_All_Questions(String Category){
        String DB_Table = Table_QUESTION_LIST;
        ArrayList<Question> found = new ArrayList<Question>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), QUESTION_LIST_FIELDS[0] + " = '" + Category + "'", null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                Question dis = new Question();
                dis.setId(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[0])));
                dis.setCategory(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[1])));
                dis.setDescription(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[2])));
                dis.setNote(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[3])));
                dis.setMandatory(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[4])));
                found.add(dis);
            }
        }catch (Exception e){

        }
        return found;
    }
    public ArrayList<Question> get_All_Questions(){
        String DB_Table = Table_QUESTION_LIST;
        ArrayList<Question> found = new ArrayList<Question>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                Question dis = new Question();
                dis.setId(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[0])));
                dis.setCategory(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[1])));
                dis.setDescription(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[2])));
                dis.setNote(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[3])));
                dis.setMandatory(c.getString(c.getColumnIndex(QUESTION_LIST_COLUMN[4])));
                found.add(dis);
            }
        }catch (Exception e){

        }
        return found;
    }
    public ArrayList<ReportItem> get_All_Report(){
        String DB_Table = Table_Report_Forms;
        ArrayList<ReportItem> found = new ArrayList<ReportItem>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                ReportItem dis = new ReportItem();
                dis.setId(c.getString(c.getColumnIndex(REPORT_FORM_COLUMN[0])));
                dis.setReport_ID(c.getString(c.getColumnIndex(REPORT_FORM_COLUMN[1])));
                dis.setCategory(c.getString(c.getColumnIndex(REPORT_FORM_COLUMN[2])));
                dis.setQuestion(c.getString(c.getColumnIndex(REPORT_FORM_COLUMN[3])));
                found.add(dis);
            }
        }catch (Exception e){
        }
        return found;
    }
    public ReportItem get_Report(String Report_ID){
        try{
            String DB_Table = Table_Reports;
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                String rep_id  = c.getString(c.getColumnIndex(REPORT_COLUMN[1]));
                if(rep_id.equals(Report_ID)){
                    ReportItem dis = new ReportItem();
                    dis.setId(c.getString(c.getColumnIndex(REPORT_COLUMN[0])));
                    dis.setReport_ID(c.getString(c.getColumnIndex(REPORT_COLUMN[1])));
                    dis.setValue(c.getString(c.getColumnIndex(REPORT_COLUMN[2])));
                    dis.setQuestion(c.getString(c.getColumnIndex(REPORT_COLUMN[1])));
                    dis.setCategory(c.getString(c.getColumnIndex(REPORT_COLUMN[1])));
                    return dis;
                }
            }
        }catch (Exception e){

        }
        return null;
    }
    public ReportItem get_Report_by_id(String ID){
        try{
            String DB_Table = Table_Reports;
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                String rep_id  = c.getString(c.getColumnIndex(REPORT_COLUMN[0]));
                if(rep_id.equals(ID)){
                    ReportItem dis = new ReportItem();
                    dis.setId(c.getString(c.getColumnIndex(REPORT_COLUMN[0])));
                    dis.setReport_ID(c.getString(c.getColumnIndex(REPORT_COLUMN[1])));
                    dis.setValue(c.getString(c.getColumnIndex(REPORT_COLUMN[2])));
                    dis.setQuestion(c.getString(c.getColumnIndex(REPORT_COLUMN[1])));
                    dis.setCategory(c.getString(c.getColumnIndex(REPORT_COLUMN[1])));
                    return dis;
                }
            }
        }catch (Exception e){

        }
        return null;
    }
    public NewsFeed get_NewsFeed_by_id(String ID){
        try{
            String DB_Table = Table_NEWSFEED;
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                String news_id  = c.getString(c.getColumnIndex(NewsFeed_COLUMN[0]));
                if(news_id.equals(ID)){
                    NewsFeed news = new NewsFeed();
                    news.setId(c.getString(c.getColumnIndex(NewsFeed_COLUMN[0])));
                    news.setNews_ID(c.getString(c.getColumnIndex(NewsFeed_COLUMN[1])));
                    news.setTitle(c.getString(c.getColumnIndex(NewsFeed_COLUMN[2])));
                    news.setContent(c.getString(c.getColumnIndex(NewsFeed_COLUMN[3])));
                    news.setImageURL(c.getString(c.getColumnIndex(NewsFeed_COLUMN[4])));
                    news.setImagePath(c.getString(c.getColumnIndex(NewsFeed_COLUMN[5])));
                    news.setPubDate(c.getString(c.getColumnIndex(NewsFeed_COLUMN[6])));
                    news.setCategory(c.getString(c.getColumnIndex(NewsFeed_COLUMN[7])));
                    return news;
                }
            }
        }catch (Exception e){

        }
        return null;
    }

    public ArrayList<String> get_all_in_column(String DB_Table,String atColumn){
        ArrayList<String> found = new ArrayList<String>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            for(int i = 0;i < c.getCount();i++){
                c.moveToPosition(i);
                String DB_Val = c.getString(c.getColumnIndex(atColumn));
                if(!found.contains(DB_Val)){
                    found.add(DB_Val);
                }
            }
        }catch (Exception e){

        }
        return found;
    }
    public ArrayList<NewsFeed> getAllNewsFeeds(){
        ArrayList<NewsFeed> found = new ArrayList<>();
        String DB_Table = Table_NEWSFEED;
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            if(c != null && c.getCount()>0){
                c.moveToFirst();
                for(int i = 0;i < c.getCount();i++){
                    c.moveToPosition(i);
                    NewsFeed news = new NewsFeed();
                    news.setId(c.getString(c.getColumnIndex(NewsFeed_COLUMN[0])));
                    news.setNews_ID(c.getString(c.getColumnIndex(NewsFeed_COLUMN[1])));
                    news.setTitle(c.getString(c.getColumnIndex(NewsFeed_COLUMN[2])));
                    news.setContent(c.getString(c.getColumnIndex(NewsFeed_COLUMN[3])));
                    news.setImageURL(c.getString(c.getColumnIndex(NewsFeed_COLUMN[4])));
                    news.setImagePath(c.getString(c.getColumnIndex(NewsFeed_COLUMN[5])));
                    news.setPubDate(c.getString(c.getColumnIndex(NewsFeed_COLUMN[6])));
                    news.setCategory(c.getString(c.getColumnIndex(NewsFeed_COLUMN[7])));
                    found.add(news);
                }
            }
        }catch (Exception e){
            return found;
        }
        return found;
    }
    public String get_DiscipleName(String phone){
        String Name = null;
        try{
            String DB_Table = Table_DISCIPLES;
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                String str = c.getString(c.getColumnIndex(DISCIPLES_COLUMN[3]));
                if(str.equals(phone)){
                    Name = c.getString(c.getColumnIndex(DISCIPLES_COLUMN[1]));
                }
            }
        }catch (Exception e){

        }
        return Name;
    }
    public int get_LogID(String Task,String Value){
        Log.i(TAG, "Get LogID:->");
		int id = 0;
		try{
            String DB_Table = Table_LOGS;
            Cursor c = myDatabase.query(DB_Table, LOGS_COLUMN, null, null, null, null, null);
            if(c != null){
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    String task = c.getString(c.getColumnIndex(LOGS_COLUMN[2]));
                    String value = c.getString(c.getColumnIndex(LOGS_COLUMN[3]));
                    Log.i(TAG, "Get LogID: " + task + " --- " + value);
                    if(Task.equals(task) && Value.equals(value)){
                        String _id = c.getString(c.getColumnIndex(LOGS_COLUMN[0]));
                        id = Integer.valueOf(_id);
                        return  id;
                    }
                }
            }
        }catch (Exception e){

        }
		return id;
	}
    public ArrayList<Disciples> getDisciples(){
        Log.i(TAG, "GetDisciples: \n");
        String DB_Table = Table_DISCIPLES;
        ArrayList<Disciples> found = new ArrayList<Disciples>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, DISCIPLES_COLUMN[0] + " DESC");
            if (c.getCount() > 0) {
                c.moveToFirst();
                for(int i=0;i<c.getCount(); i++) {

                    c.moveToPosition(i);
                    Disciples dis = new Disciples();
                    dis.setId(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[0])));
                    dis.setFull_Name(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[1])));
                    dis.setEmail(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[2])));
                    dis.setPhone(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[3])));
                    dis.setCountry(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[4])));
                    dis.setBuild_Phase(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[5])));
                    dis.setGender(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[6])));
                    dis.setPicture(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[7])));
                    Log.i(TAG, "Found Disciples:-> "+dis.toString());
                    found.add(dis);
                }
            }
        }catch (Exception e){

        }
        return found;
    }


     public ArrayList<Schedule> get_All_Schedule(){
         Log.i(TAG, "GetAll Schedule:\n");
        String DB_Table = Table_SCHEDULES;
        ArrayList<Schedule> found = new ArrayList<Schedule>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, SCHEDULES_COLUMN[0] + " DESC");
            if (c.getCount() > 0) {
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    Schedule dis = new Schedule();
                    dis.setID(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[0])));
                    dis.setDisciple_Phone(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[1])));
                    dis.setTitle(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[2])));
                    dis.setAlarm_Time(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[3])));
                    dis.setAlarm_Repeat(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[4])));
                    dis.setDescription(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[5])));
                    Log.i(TAG, "Found Schedules:-> "+dis.toString());
                    found.add(dis);
                }
            }
        }catch (Exception e){

        }
        return found;
    }
    public ArrayList<Country> get_All_Country(){
        Log.i(TAG, "GetAll Countries:\n");
        String DB_Table = Table_COUNTRY;
        ArrayList<Country> found = new ArrayList<Country>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    Country dis = new Country();
                    dis.setId(c.getString(c.getColumnIndex(COUNTRY_COLUMN[0])));
                    dis.setCountry_id(c.getString(c.getColumnIndex(COUNTRY_COLUMN[1])));
                    dis.setIso3(c.getString(c.getColumnIndex(COUNTRY_COLUMN[2])));
                    dis.setName(c.getString(c.getColumnIndex(COUNTRY_COLUMN[3])));
                    dis.setCode(c.getString(c.getColumnIndex(COUNTRY_COLUMN[4])));
                    Log.i(TAG, "Found Countries:-> "+dis.toString());
                    found.add(dis);
                }
            }
        }catch (Exception e){

        }
        return found;
    }
    public Country get_Country_by_CountryID(String CountryID){
        try{
            Log.i(TAG, "GetAll Country by CountryID:\n");
            String DB_Table = Table_COUNTRY;
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    String id = c.getString(c.getColumnIndex(COUNTRY_COLUMN[1]));
                    if(id.equals(CountryID)){
                        Country dis = new Country();
                        dis.setId(c.getString(c.getColumnIndex(COUNTRY_COLUMN[0])));
                        dis.setCountry_id(c.getString(c.getColumnIndex(COUNTRY_COLUMN[1])));
                        dis.setIso3(c.getString(c.getColumnIndex(COUNTRY_COLUMN[2])));
                        dis.setName(c.getString(c.getColumnIndex(COUNTRY_COLUMN[3])));
                        dis.setCode(c.getString(c.getColumnIndex(COUNTRY_COLUMN[4])));
                        Log.i(TAG, "Found Country:-> "+dis.toString());
                        return dis;
                    }
                }
            }
        }catch (Exception e){

        }
        return null;
    }
    public ArrayList<Schedule> get_Schedule_With_User(String Dis_Phone){
        String DB_Table = Table_SCHEDULES;
        ArrayList<Schedule> found = new ArrayList<Schedule>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++){
                c.moveToPosition(i);
                Schedule dis = new Schedule();
                String phone = c.getString(c.getColumnIndex(SCHEDULES_COLUMN[1]));
                if(Dis_Phone.equals(phone)){
                    dis.setID(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[0])));
                    dis.setDisciple_Phone(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[1])));
                    dis.setTitle(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[2])));
                    dis.setAlarm_Time(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[3])));
                    dis.setAlarm_Repeat(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[4])));
                    dis.setDescription(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[5])));
                    Log.i(TAG, "Found Schedules:->:" + dis.toString());
                    found.add(dis);
                }
            }
        }catch (Exception e){
            return found;
        }
        return found;
    }


    public Schedule getScheduleWithId(String id){
        try{
            Schedule dis = new Schedule();
            String DB_Table = Table_SCHEDULES;
            Cursor c = myDatabase.rawQuery("select * from " + DB_Table + " where id=" + id, null);
            c.moveToFirst();
            if(c.getCount()>0){
                dis.setID(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[0])));
                dis.setDisciple_Phone(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[1])));
                dis.setTitle(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[2])));
                dis.setAlarm_Time(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[3])));
                dis.setAlarm_Repeat(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[4])));
                dis.setDescription(c.getString(c.getColumnIndex(SCHEDULES_COLUMN[5])));
                return dis;
            }
        }catch (Exception e){

        }
        return null;
    }

    public Disciples getDiscipleProfile(String Dis_ID){
        try{
            Log.i(TAG, "GetDiscipleProfile");
            Disciples dis = new Disciples();
            String DB_Table = Table_DISCIPLES;
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, SCHEDULES_COLUMN[0] + " DESC");
            if(c != null){
                c.moveToFirst();
                for(int i=0; i<c.getCount();i++){
                    c.moveToPosition(i);
                    String ID = c.getString(c.getColumnIndex(DISCIPLES_COLUMN[0]));
                    Log.i(TAG, "GetDiscipleProfile Comparing: "+ID+" | "+Dis_ID);
                    if(ID.equals(Dis_ID)){
                        dis.setId(Dis_ID);
                        dis.setFull_Name(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[1])));
                        dis.setEmail(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[2])));
                        dis.setPhone(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[3])));
                        dis.setCountry(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[4])));
                        dis.setBuild_Phase(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[5])));
                        dis.setGender(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[6])));
                        dis.setPicture(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[7])));
                        Log.i(TAG, "Found DiscipleProfile:-> " + dis.toString());
                        return dis;
                    }
                }
            }
        }catch (Exception e){

        }
        return null;
    }


    public Disciples getDiscipleProfileFromPhone(String Dis_Phone){
        try{
            Log.i(TAG, "GetDiscipleProfile");
            Disciples dis = new Disciples();
            String DB_Table = Table_DISCIPLES;
            Cursor c = myDatabase.rawQuery("select * from " + DB_Table + " where " + DISCIPLES_COLUMN[3] + "= '" + Dis_Phone + "'", null);
            if(c.getCount()>0){
                c.moveToFirst();
                dis.setId(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[0])));
                dis.setFull_Name(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[1])));
                dis.setEmail(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[2])));
                dis.setPhone(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[3])));
                dis.setCountry(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[4])));
                dis.setBuild_Phase(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[5])));
                dis.setGender(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[6])));
                dis.setPicture(c.getString(c.getColumnIndex(DISCIPLES_COLUMN[7])));
                Log.i(TAG, "Found DiscipleProfile:-> " + dis.toString());
                return dis;
            }

        }catch (Exception e){

        }
        return null;
    }

    public User getUserProfile(){
        User dis = new User();
        try{
            String DB_Table = Table_USER;
            Cursor c = myDatabase.rawQuery("select * from " + DB_Table, null);
            c.moveToFirst();
            if(c.getCount()>0){
                dis.setId(c.getString(c.getColumnIndex(USER_COLUMN[0])));
                dis.setUser_Name(c.getString(c.getColumnIndex(USER_COLUMN[1])));
                dis.setUser_Email(c.getString(c.getColumnIndex(USER_COLUMN[2])));
                dis.setUser_Phone(c.getString(c.getColumnIndex(USER_COLUMN[3])));
                dis.setUser_Pass(c.getString(c.getColumnIndex(USER_COLUMN[4])));
                dis.setUser_Country(c.getString(c.getColumnIndex(USER_COLUMN[5])));
                dis.setUser_Picture(c.getString(c.getColumnIndex(USER_COLUMN[6])));
                dis.setUser_Favorite_Scripture(c.getString(c.getColumnIndex(USER_COLUMN[7])));
                return dis;
            }
        }catch (Exception e){

        }
        return null;
    }


    public long checkExistence(String Db_Table,String column,String id, String build){

        Cursor cursor = myDatabase.query(Db_Table, getColumns(Db_Table),column+" = '"+id+"' and "+QUESTION_ANSWER_FIELDS[3]+" = '"+ build+"'",null,null,null,null);
        return cursor.getCount();

    }
    public int count_Questions(String DB_Table, String Category){
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), QUESTION_LIST_FIELDS[0]+" = '"+Category+"'", null, null, null, null);
        return c.getCount();
    }

    public ArrayList<QuestionAnswer> get_Answer(String Dis_ID, String phase){
        String DB_Table = Table_QUESTION_ANSWER;
        ArrayList<QuestionAnswer> found = new ArrayList<QuestionAnswer>();
        try{
            Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), QUESTION_ANSWER_FIELDS[0] + " = '" + Dis_ID + "' and " + QUESTION_ANSWER_FIELDS[3] + "= '" + phase + "'", null, null, null, null);
            Log.i("Deep Life", "Answer from db count: " + c.getCount() + " data: " + c.toString());
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                QuestionAnswer dis = new QuestionAnswer();
                dis.setId(c.getString(c.getColumnIndex(QUESTION_ANSWER_COLUMN[0])));
                dis.setDisciple_id(c.getString(c.getColumnIndex(QUESTION_ANSWER_COLUMN[1])));
                dis.setQuestion_id(c.getString(c.getColumnIndex(QUESTION_ANSWER_COLUMN[2])));
                dis.setAnswer(c.getString(c.getColumnIndex(QUESTION_ANSWER_COLUMN[3])));
                dis.setBuild_Phase(c.getString(c.getColumnIndex(QUESTION_ANSWER_COLUMN[4])));

                found.add(dis);
            }
        }catch (Exception e){

        }
        return found;
    }
    public User getUser(){
        User newUser = new User();
        try{
            Cursor c = myDatabase.query(Table_USER, USER_COLUMN, null, null, null, null, null);
            if(c != null && c.getCount() == 1){
                c.moveToFirst();
                newUser.setId(c.getString(c.getColumnIndex(USER_COLUMN[0])));
                newUser.setUser_Name(c.getString(c.getColumnIndex(USER_COLUMN[3])));
                newUser.setUser_Pass(c.getString(c.getColumnIndex(USER_COLUMN[4])));
                newUser.setUser_Country(c.getString(c.getColumnIndex(USER_COLUMN[5])));
            }else{
                newUser = null;
            }
        }catch (Exception e){

        }
        return newUser;
    }
    public ArrayList<Logs> getSendLogs(){
        Log.i(TAG, "SendLogs:\n");
        ArrayList<Logs> Found = new ArrayList<>();
        try{
            Cursor c = myDatabase.query(Table_LOGS, LOGS_COLUMN, null, null, null, null, null);
            if(c != null && c.getCount()>0){
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    String str = c.getString(c.getColumnIndex(LOGS_COLUMN[2]));
                    Log.i(TAG, "Comparing-> \n" + SyncService.Sync_Tasks[0] + " | "+str);
                    if (SyncService.Sync_Tasks[0].equals(str)){
                        Log.i(TAG, "SendLogs Count:-> " + c.getCount());
                        Logs newLogs = new Logs();
                        newLogs.setId(c.getString(c.getColumnIndex(LOGS_COLUMN[0])));
                        newLogs.setType(c.getString(c.getColumnIndex(LOGS_COLUMN[1])));
                        newLogs.setTask(c.getString(c.getColumnIndex(LOGS_COLUMN[2])));
                        newLogs.setValue(c.getString(c.getColumnIndex(LOGS_COLUMN[3])));
                        Log.i(TAG, "Found for SendLogs:-> \n" + newLogs.toString());
                        Found.add(newLogs);
                    }
                }
            }
        }catch (Exception e){

        }
        return Found;
    }
    public ArrayList<Disciples> getSendDisciples(){
        Log.i(TAG, "SendDisciples:\n");
        ArrayList<Disciples> Found = new ArrayList<>();
        try{
            Cursor c = myDatabase.query(Table_LOGS, LOGS_COLUMN, null, null, null, null, null);
            if(c != null && c.getCount()>0){
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    String str = c.getString(c.getColumnIndex(LOGS_COLUMN[2]));
                    String id = c.getString(c.getColumnIndex(LOGS_COLUMN[3]));
                    String ID = c.getString(c.getColumnIndex(LOGS_COLUMN[0]));
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
        }catch (Exception e){

        }
        return Found;
    }
    public ArrayList<Schedule> getSendSchedules(){
        Log.i(TAG, "SendSchedules:\n");
        ArrayList<Schedule> Found = new ArrayList<>();
        try{
            Cursor c = myDatabase.query(Table_LOGS, LOGS_COLUMN, null, null, null, null, null);
            if(c != null && c.getCount()>0){
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    String str = c.getString(c.getColumnIndex(LOGS_COLUMN[2]));
                    String id = c.getString(c.getColumnIndex(LOGS_COLUMN[3]));
                    String ID = c.getString(c.getColumnIndex(LOGS_COLUMN[0]));
                    Log.i(TAG, "SendSchedule Comparing-> \n" + SyncService.Sync_Tasks[4] + " | "+str);
                    if(SyncService.Sync_Tasks[4].equals(str)){
                        Log.i(TAG, "SendSchedule Count:-> " + c.getCount());
                        Schedule newSchedule = getScheduleWithId(id);
                        if(newSchedule !=null){
                            newSchedule.setID(ID);
                            Log.i(TAG, "Found for SendSchedules:-> \n" + newSchedule.toString());
                            Found.add(newSchedule);
                        }
                    }
                }
            }
        }catch (Exception e){

        }
        return Found;
    }
    public ArrayList<ReportItem> getSendReports(){
        Log.i(TAG, "SendReports:\n");
        ArrayList<ReportItem> Found = new ArrayList<>();
        try{
            Cursor c = myDatabase.query(Table_LOGS, LOGS_COLUMN, null, null, null, null, null);
            if(c != null && c.getCount()>0){
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    String str = c.getString(c.getColumnIndex(LOGS_COLUMN[2]));
                    String id = c.getString(c.getColumnIndex(LOGS_COLUMN[3]));
                    String ID = c.getString(c.getColumnIndex(LOGS_COLUMN[0]));
                    Log.i(TAG, "Comparing-> \n" + SyncService.Sync_Tasks[5] + " | "+str);
                    if(SyncService.Sync_Tasks[5].equals(str)){
                        Log.i(TAG, "SendDisciples Count:-> " + c.getCount());
                        ReportItem newReport = get_Report_by_id(id);
                        if(newReport !=null){
                            newReport.setId(ID);
                            Log.i(TAG, "Found for Send:-> \n" + newReport.toString());
                            Found.add(newReport);
                        }
                    }
                }
            }
        }catch (Exception e){

        }
        return Found;
    }
    public ArrayList<Disciples> getUpdateDisciples(){
        Log.i(TAG, "UpdateDisciples:\n");
        ArrayList<Disciples> Found = new ArrayList<>();
        try{
            Cursor c = myDatabase.query(Table_LOGS, LOGS_COLUMN, null, null, null, null, null);
            if(c != null && c.getCount()>0){
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    c.moveToPosition(i);
                    String ID = c.getString(c.getColumnIndex(LOGS_COLUMN[0]));
                    String str = c.getString(c.getColumnIndex(LOGS_COLUMN[2]));
                    String id = c.getString(c.getColumnIndex(LOGS_COLUMN[3]));
                    Log.i(TAG, "Comparing-> \n" + SyncService.Sync_Tasks[3] + " | "+str);
                    if(SyncService.Sync_Tasks[3].equals(str)){
                        Log.i(TAG, "UpdateDisciples Count:-> " + c.getCount());
                        Disciples newDisciples = getDiscipleProfile(id);
                        if(newDisciples !=null){
                            newDisciples.setId(ID);
                            Found.add(newDisciples);
                        }
                    }
                }
            }
        }catch (Exception e){

        }
        return Found;
    }



    private String[] getColumns(String DB_Table){
        String[] strs = null;
        if(DB_Table == Table_DISCIPLES){
            strs = DISCIPLES_COLUMN;
        }else if(DB_Table == Table_LOGS){
            strs = LOGS_COLUMN;
        }else if(DB_Table == Table_USER){
            strs = USER_COLUMN;
        }else if(DB_Table == Table_SCHEDULES){
            strs = SCHEDULES_COLUMN;
        } else if(DB_Table == Table_QUESTION_LIST){
            strs = QUESTION_LIST_COLUMN;
        } else if(DB_Table == Table_QUESTION_ANSWER){
            strs = QUESTION_ANSWER_COLUMN;
        } else if(DB_Table == Table_Reports){
            strs = REPORT_COLUMN;
        }else if(DB_Table == Table_Report_Forms){
            strs = REPORT_FORM_COLUMN;
        }else if(DB_Table == Table_COUNTRY){
            strs = COUNTRY_COLUMN;
        }else if(DB_Table == Table_NEWSFEED){
            strs = NewsFeed_COLUMN;
        }
        return strs;
    }
}
