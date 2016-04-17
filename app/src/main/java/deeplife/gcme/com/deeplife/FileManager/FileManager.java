package deeplife.gcme.com.deeplife.FileManager;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by media on 11/11/15.
 */
public class FileManager {
    private Context myContext;
    private File myFile;
    public FileManager(Context context){
        myContext = context;
        if(isExternalStorageWritable()){
            myFile = new File(Environment.getExternalStorageDirectory(), "DeepLife");
            if(!myFile.isDirectory()){
                myFile.mkdir();
            }
        }
        else{
            myFile = new File(context.getFilesDir().getPath(), "DeepLife");
            if(!myFile.isDirectory()){
                myFile.mkdir();
            }
        }
        createFolder("images");
    }
    public boolean createFolder(String FolderName){
        File Folder = new File(myFile,FolderName);
        if(!Folder.isDirectory()){
            Folder.mkdir();
            return true;
        }else{
            return true;
        }
    }
    public File getFile(String name){
        File file = new File(myFile,name);
        return file;
    }
    public File createFileAt(String FolderName,String FileName) {
        if (createFolder(FolderName)) {
            File file = new File(getFile(FolderName), FileName);
            return file;
        } else {
            return null;
        }
    }
    public File getFileAt(String FolderName,String FileName){
        createFolder(FolderName);
        File file = new File(getFile(FolderName), FileName);
        return file;
    }
    public void CopyFile(File source,File destination) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(destination);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
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
