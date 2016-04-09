package deeplife.gcme.com.deeplife.Models;

public class Schedule {
	private String ID, Disciple_Phone, Alarm_Time, Alarm_Repeat, Description, Title;

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

	public String getDisciple_Phone() {
		return Disciple_Phone;
	}

	public void setDisciple_Phone(String disciple_Phone) {
		Disciple_Phone = disciple_Phone;
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
