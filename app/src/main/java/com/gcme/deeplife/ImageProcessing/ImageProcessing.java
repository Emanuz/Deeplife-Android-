package com.gcme.deeplife.ImageProcessing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Roger on 3/27/2016.
 */
public class ImageProcessing {


    public static String storageDir = Environment.getExternalStorageDirectory() + "/deeplifetest";
    public static String DEFAULT_PATH = "/storage/emulated/0/deeplifetest/myprofile/JPEG_20160327_193322_.jpg";
    public static Bitmap profilepic = null;
    private static String TAG = "Deep Life";

    public static File createImageFile(String task) {
        // Create an image file name

        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File dir = new File(storageDir +"/" + task);
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    Log.i("Deep Life", "Directory created!");
                }
            }
            File image = new File(storageDir + "/" + task + "/" + imageFileName + ".jpg");

            // Save a file: path for use with ACTION_VIEW intents
            //newCurrentPhotoPath = image.getAbsolutePath();
            return image;
        }catch(Exception e){
            e.getMessage();
        }
        return null;
    }

    public static Bitmap getProfileImage(final Context context, final String path, final int width, final int height) {

        final Bitmap finalPic = null;
//        Bitmap b = BitmapFactory.decodeFile(path);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Looper.prepare();

                try {
                    profilepic = Glide.with(context).load(path).asBitmap().into(height, width).get();
                } catch (ExecutionException e) {
                    Log.e(TAG, e.getMessage());
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void dummy) {
                if (profilepic != null) {
                    finalPic.createBitmap(profilepic);
                }
            }
        }.execute();

        return finalPic;
    }

}
