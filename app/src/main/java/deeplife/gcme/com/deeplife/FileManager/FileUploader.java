//package deeplife.gcme.com.deeplife.FileManager;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Environment;
//import android.os.RecoverySystem;
//import android.util.Log;
//import android.widget.Toast;
//
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import deeplife.gcme.com.deeplife.DeepLife;
//
//
///**
// * Created by bengeos on 8/1/16.
// */
//
//public class FileUploader extends AsyncTask<String,Integer,String> {
//    public long totalSize = 1;
//    private String filePath = "";
//    private String responses = "";
//    private Context myContext = null;
//    private String UserName,UserPass,Service,Param,Country;
//    private boolean isRunning = true;
//    private int progress = 0;
//    private static final String TAG = "Uploading";
//
//    public FileUploader(Context context,String file_path,String username, String userpass, String service, String param, String country){
//        this.UserName = username;
//        this.UserPass = userpass;
//        this.Service = service;
//        this.Param = param;
//        this.Country = country;
//        this.myContext = context;
//        this.filePath = file_path;
//    }
//    public void setParams(String file_path,String username, String userpass, String service, String param, String country){
//        this.UserName = username;
//        this.UserPass = userpass;
//        this.Service = service;
//        this.Param = param;
//        this.Country = country;
//        this.filePath = file_path;
//    }
//    public boolean isRunning(){
//        return isRunning;
//    }
//    public int getProgress(){
//        return progress;
//    }
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        Toast.makeText(myContext,"Uploading Started",Toast.LENGTH_LONG).show();
//        this.isRunning = true;
//
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//        isRunning = true;
//        String res = upload_File("/storage/emulated/0/DeepLife/disciples/JPEG_20160803_031807_.jpg");
//        Log.i(TAG,"Upload Response: \n"+res);
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//        isRunning = false;
//    }
//
//    @Override
//    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
//        progress = values[0];
//    }
//
//    private String UploadFile(String file_Path) {
//        String responseString = null;
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(DeepLife.API_URL);
//        try {
//            My_MultiPartEntity entity = new My_MultiPartEntity(new My_MultiPartEntity.ProgressListener() {
//                @Override
//                public void transferred(long num) {
//                    publishProgress((int) ((num / (float) totalSize) * 100));
//                }
//            });
//            File sourceFile = new File(file_Path);
//            entity.addPart("FileUpload", new FileBody(sourceFile));
//            entity.addPart("User_Name", new StringBody(UserName));
//            entity.addPart("User_Pass", new StringBody(UserPass));
//            entity.addPart("Service", new StringBody(Service));
//            entity.addPart("Param", new StringBody(Param));
//            entity.addPart("Country", new StringBody(Country));
//            // -->>
//         //   Toast.makeText(myContext,"Uploading File: "+entity.toString(),Toast.LENGTH_LONG).show();
//            totalSize = entity.getContentLength();
//            httpPost.setEntity(entity);
//
//            HttpResponse response = httpClient.execute(httpPost);
//            HttpEntity r_Entity = response.getEntity();
//
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode == 200) {
//                // Server response
//                responseString = EntityUtils.toString(r_Entity);
//            } else {
//                responseString = "Error occurred! Http Status Code: "
//                        + statusCode;
//            }
//        } catch (ClientProtocolException e) {
//            responseString = e.toString();
//        } catch (IOException e) {
//            responseString = e.toString();
//        } catch (Exception e) {
//            responseString = e.toString();
//        }
//        return responseString;
//    }
//    public int uploadFile(String sourceFileUri) {
//
//
//        String fileName = sourceFileUri;
//        HttpURLConnection conn = null;
//        DataOutputStream dos = null;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1 * 1024 * 1024;
//        File sourceFile = new File(sourceFileUri);
//        Log.i(TAG, "Uploading: Initialsing ... " );
//
//        try {
//
//            // open a URL connection to the Servlet
//
//            FileInputStream fileInputStream = new FileInputStream(sourceFile);
//            URL url = new URL(DeepLife.API_URL);
//            Log.i(TAG, "Uploading: Preparing stream input " );
//
//            // Open a HTTP  connection to  the URL
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setDoInput(true); // Allow Inputs
//            conn.setDoOutput(true); // Allow Outputs
//            conn.setUseCaches(false); // Don't use a Cached Copy
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//            conn.setRequestProperty("User_Name", UserName);
//            conn.setRequestProperty("User_Pass", UserPass);
//            conn.setRequestProperty("Service", Service);
//            conn.setRequestProperty("Param", Param);
//            conn.setRequestProperty("Country", Country);
//
//
//            Log.i(TAG, "Uploading: set request parameters ... " );
//
//            dos = new DataOutputStream(conn.getOutputStream());
//
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"FileUpload\";filename=\""
//                    + fileName + "\"" + lineEnd);
//
//            dos.writeBytes(lineEnd);
//
//            // create a buffer of  maximum size
//            bytesAvailable = fileInputStream.available();
//            Log.i(TAG,"Available Bytes: "+conn.toString());
//
//            bufferSize = Math.min(bytesAvailable, maxBufferSize);
//            buffer = new byte[bufferSize];
//
//            // read file and write it into form...
//            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//            while (bytesRead > 0) {
//
//                dos.write(buffer, 0, bufferSize);
//                bytesAvailable = fileInputStream.available();
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//            }
//
//            // send multipart form data necesssary after file data...
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//            // Responses from the server (code and message)
//            int serverResponseCode = conn.getResponseCode();
//            Log.i(TAG,String.valueOf(bytesRead));
//            String serverResponseMessage = conn.getResponseMessage();
//            Log.i(TAG, "HTTP Response is : "
//                    + serverResponseMessage + ": " + serverResponseCode);
//
//
//            //close the streams //
//            fileInputStream.close();
//            dos.flush();
//            dos.close();
//
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//            Log.i(TAG, "error: " + ex.getMessage(), ex);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i(TAG, "error: " + e.getMessage());
//
//        }
//        return 0;
//
//    }
//    private void postFile(String filePath){
//        try{
//            // new HttpClient
//            HttpClient httpClient = new DefaultHttpClient();
//
//            // post header
//            HttpPost httpPost = new HttpPost(DeepLife.API_URL);
//
//            File file = new File(filePath);
//            FileBody fileBody = new FileBody(file);
//
//            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//            reqEntity.addPart("FileUpload", fileBody);
//           // reqEntity.addPart("User_Name",new StringBody(UserName));
//
//            httpPost.setEntity(reqEntity);
//
//            // execute HTTP post request
//            HttpResponse response = httpClient.execute(httpPost);
//            HttpEntity resEntity = response.getEntity();
//
//            if (resEntity != null) {
//
//                String responseStr = EntityUtils.toString(resEntity).trim();
//                Log.v(TAG, "Response: " +  responseStr);
//
//                // you can add an if statement here and do other actions based on the response
//            }
//
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @SuppressWarnings("deprecation")
//    private String upload_File(String file_path) {
//        String responseString = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpContext localContext = new BasicHttpContext();
//        HttpPost httppost = new HttpPost(DeepLife.API_URL);
//
//        try {
////            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
////                    new AndroidMultiPartEntity.ProgressListener() {
////                        @Override
////                        public void transferred(long num) {
////                            publishProgress((int) ((num / (float) totalSize) * 100));
////                        }
////                    });
//            MultipartEntity entity = new MultipartEntity(
//                    HttpMultipartMode.BROWSER_COMPATIBLE);
//            File sourceFile = new File(file_path);
//
//            // Adding file data to http body
//            entity.addPart("image", new FileBody(sourceFile));
//
//            // Extra parameters if you want to pass to server
//            entity.addPart("website",
//                    new StringBody("www.androidhive.info"));
//            entity.addPart("email", new StringBody("abc@gmail.com"));
//
////            totalSize = entity.getContentLength();
////            httppost.setEntity(entity);
////
////            Log.i(TAG, "Entity->: " + entity.toString());
////            // Making server call
////            HttpResponse response = httpclient.execute(httppost);
////            HttpEntity r_entity = response.getEntity();
////
////            int statusCode = response.getStatusLine().getStatusCode();
////            if (statusCode == 200) {
////                // Server response
////                responseString = EntityUtils.toString(r_entity);
////            } else {
////                responseString = "Error occurred! Http Status Code: "
////                        + statusCode;
////            }
//            httppost.setEntity(entity);
//            HttpResponse response = httpclient.execute(httppost,
//                    localContext);
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(
//                            response.getEntity().getContent(), "UTF-8"));
//
//            responseString = reader.readLine();
//        } catch (ClientProtocolException e) {
//            responseString = "Client Protocol Error:\n"+e.toString();
//        } catch (IOException e) {
//            responseString = "Exception Error:\n"+e.toString();
//        }
//
//        return responseString;
//
//    }
//}
