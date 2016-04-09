package deeplife.gcme.com.deeplife.Models;

public class Disciples {
	private String id,Full_Name,Phone,Email,Country,Build_Phase,Gender,Picture;
	
	public Disciples(String Full_Name, String middleName, String phone,
			String email, String country,String Build_Phase) {
		super();
		this.Full_Name = Full_Name;
		Phone = phone;
		Email = email;
		Country = country;
		this.Build_Phase = Build_Phase;
	}
	public Disciples(){
		
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getPicture() {
		return Picture;
	}

	public void setPicture(String picture) {
		Picture = picture;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getFull_Name() {return Full_Name;	}

	public void setFull_Name(String full_Name) {Full_Name = full_Name;	}

	public String getBuild_Phase() {return Build_Phase;	}

	public void setBuild_Phase(String build_Phase) {Build_Phase = build_Phase;	}


	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}
	
}
