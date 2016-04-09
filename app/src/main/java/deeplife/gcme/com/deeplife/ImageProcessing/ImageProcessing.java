package deeplife.gcme.com.deeplife.ImageProcessing;

import android.content.Context;
import android.os.Environment;

import deeplife.gcme.com.deeplife.FileManager.FileManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Roger on 3/27/2016.
 */
public class ImageProcessing {

    public String storageDir;
    Context myContext;

    FileManager fileManager;
    public ImageProcessing(Context context){
        fileManager = new FileManager(context);
    }

    public File createImage(String folder) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_"+".jpg";
        File newImage = fileManager.createFileAt(folder,imageFileName);
        return newImage;

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
