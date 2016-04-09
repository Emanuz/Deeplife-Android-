package deeplife.gcme.com.deeplife.Disciples;

/**
 * Created by BENGEOS on 3/25/16.
 */
public class DiscipleObject {
    private String FullName,ImageURL,Phone,Email,Image;

    public DiscipleObject() {
        FullName = "";
        ImageURL = "";
        Phone = "";
        Email = "";
        Image = "";
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
