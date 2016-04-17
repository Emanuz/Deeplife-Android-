package deeplife.gcme.com.deeplife.FileManager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import deeplife.gcme.com.deeplife.DeepLife;

/**
 * Created by BENGEOS on 4/16/16.
 */
public class FileDownloader extends AsyncTask<String,String,String> {
    private FileManager fileManager;
    private Context myContext;
    private String fileName,folderName,fileURL;
    public static final String TAG = "FileDownloader";


    public FileDownloader(Context context,String file_url,String folder_Name,String file_Name){
        this.myContext = context;
        this.fileName = file_Name;
        this.folderName = folder_Name;
        this.fileURL = file_url;
        fileManager = new FileManager(context);

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        DeepLife.DOWNLOAD_STATUS += 1;
        Log.i(TAG, "Start Downloading "+fileName);
        Log.i(TAG, "Downloading From: ->" + fileURL);
    }

    @Override
    protected String doInBackground(String... params) {
        Download(fileURL,fileName,folderName);
        Log.i(TAG, "Downloading " + fileName+ "Finished");
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        DeepLife.DOWNLOAD_STATUS -= 1;
    }
    protected void Download(String url,String filename,String foldername){
        int count = 0;
        long total = 0;
        try{
            URL web_url = new URL(url);
            URLConnection connection = web_url.openConnection();
            connection.connect();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            int filelength = connection.getContentLength();
            InputStream input = new BufferedInputStream(web_url.openStream());
            File destination = fileManager.createFileAt(foldername,filename);
            Log.i(TAG, "Download Destination "+destination.getAbsolutePath());
            OutputStream output = new FileOutputStream(destination);
            byte data[] = new byte[1024];
            while((count = input.read(data)) != -1){
                total += count;
                output.write(data,0,count);
            }
            Log.i(TAG, "Downloaded " + filename+":->"+total);
            output.flush();
            output.close();
            input.close();
        }catch (Exception e){
            Log.i(TAG, "Downloading "+filename+" interrupted :-> "+total);
            Log.i(TAG, "Downloading error"+e.toString());
        }
    }
}
