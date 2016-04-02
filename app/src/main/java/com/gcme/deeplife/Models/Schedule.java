package com.gcme.deeplife.Models;

public class Schedule {
	private String ID,User_Id,Alarm_Time,Alarm_Repeat,Description, Title;

	public Schedule() {
		super();
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(String user_Id) {
		User_Id = user_Id;
	}

	public String getAlarm_Time() {
		return Alarm_Time;
	}

	public void setAlarm_Time(String alarm_Time) {
		Alarm_Time = alarm_Time;
	}

	public String getAlarm_Repeat() {
		return Alarm_Repeat;
	}

	public void setAlarm_Repeat(String alarm_Repeat) {
		Alarm_Repeat = alarm_Repeat;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	
}
