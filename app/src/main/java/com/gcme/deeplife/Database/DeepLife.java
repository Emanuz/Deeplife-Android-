package com.gcme.deeplife.Database;

public class DeepLife {

    public static final String Table_DISCIPLES = "DISCIPLES";
    public static final String Table_SCHEDULES = "SCHEDULES";
    public static final String Table_LOGS = "LOGS";
    public static final String Table_USER = "USER";
    public static final String Table_COUNTRY = "COUNTRIES";
    public static final String Table_Reports = "Reports";
    public static final String Table_Report_Forms = "Report_Form";
    public static final String Table_QUESTION_LIST = "QUESTION_LIST";
    public static final String Table_QUESTION_ANSWER = "QUESTION_ANSWER";



    public static final String[] DISCIPLES_FIELDS = {"Full_Name", "Email", "Phone", "Country","Build_phase","Gender","Picture" };
    public static final String[] LOGS_FIELDS = { "Type", "Task","Value" };
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
    public static final String[] REPORT_COLUMN = {"id","Report_ID","Value","Date"};
    public static final String[] COUNTRY_COLUMN = {"id", "Country_id", "iso3","name","code" };
    public static final String[] LOGS_COLUMN = { "id", "Type", "Task","Value" };
    public static final String[] USER_COLUMN = { "id", "Full_Name", "Email","Phone","Password","Country","Picture","Favorite_Scripture" };
    public static final String[] QUESTION_LIST_COLUMN = {"id","Category","Description", "Note","Mandatory"};
    public static final String[] QUESTION_ANSWER_COLUMN = {"id", "Disciple_Phone","Question_ID", "Answer","Build_Stage"};

    public static final String TAG = "Deep Life";

}
