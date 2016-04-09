package deeplife.gcme.com.deeplife.Helps;

/**
 * Created by BENGEOS on 2/19/16.
 */
public class HelpDataType {
    private String Title;
    private int ImageID;

    public HelpDataType(int imageID, String title) {
        ImageID = imageID;
        Title = title;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
