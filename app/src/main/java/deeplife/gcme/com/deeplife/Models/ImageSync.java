package deeplife.gcme.com.deeplife.Models;

/**
 * Created by bengeos on 8/2/16.
 */

public class ImageSync {
    private String id,FilePath,Image,Param;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getParam() {
        return Param;
    }

    public void setParam(String param) {
        Param = param;
    }
}
