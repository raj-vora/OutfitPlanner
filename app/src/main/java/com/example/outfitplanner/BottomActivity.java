package com.example.outfitplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.OpenCVLoader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BottomActivity extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String currentPhotoPathBottom="";
    String topColor, bottomColor;
    Bitmap imageBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OpenCVLoader.initDebug();
        setContentView(R.layout.activity_bottom);
        Intent intent = getIntent();
        topColor = intent.getStringExtra("topColor");
    }

    public void chooseBottom(View view){
        Intent takePicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getPackageManager())!=null){
            File photoFile=null;
            try{
                photoFile=createImageFile();
            }catch(IOException ix){
                //Error
                Log.i( "Info","Error");
            }
            if(photoFile!=null){
                Uri photoURI= FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
//                fileName+=photoURI.toString();
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePicture, REQUEST_TAKE_PHOTO);
            }
        }
        //start camera module

        //save the image

        //set the image in imageView
    }
    private File createImageFile()throws IOException {
        //Create file name
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="BOT_"+timeStamp+"_";
//        fileName+=imageFileName;
        File storageDir= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image=File.createTempFile(imageFileName,".jpg",storageDir);
        currentPhotoPathBottom= image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);
        File imgFile = new  File(currentPhotoPathBottom);
        if(resultCode==RESULT_OK) {
            if(imgFile.exists()) {
                imageBottom = BitmapFactory.decodeFile(currentPhotoPathBottom);
                ImageView iv = findViewById(R.id.bottomImageViewB);
                iv.setImageBitmap(imageBottom);
                String encodedString;
                try{
                    final InputStream inStreamImage = new FileInputStream(currentPhotoPathBottom);
                    byte[] bytes;
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    while ((bytesRead = inStreamImage.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                    bytes = output.toByteArray();
                    encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                    uploadImage(encodedString);
                } catch (Exception e) {
                    Log.e("File issues", "Issues in File part");
                    e.printStackTrace();
                }
            }
        }
    }

    public void uploadImage(String encodedImageBottom) {
        Log.i("Url",encodedImageBottom);
        JSONObject json = new JSONObject();

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(BottomActivity.this);
            json.put("data",encodedImageBottom);
            json.put("auth","");
            final String jsonString = json.toString();
            String url = "https://outfitplanner-api.herokuapp.com";
            Log.i("Volley","starting API stuff");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String response){
                    Log.i("LOG VOLLEY","reposnse "+response);
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.e("LOG ERROR",error.toString());
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return jsonString.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonString, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    if (response != null) {
//                        responseString = String.valueOf(response.data);
                        bottomColor = new String(response.data);
                        Log.i("respo",bottomColor);
                    }
                    return Response.success(bottomColor, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e){ e.printStackTrace();}
    }

    public void goToTop(View view){
        Intent intent = new Intent(this, TopActivity.class);
//        intent.putExtra("topColor",topColor);
//        intent.putExtra("bottomColor", bottomColor);
        startActivity(intent);
    }

    public void goToResult(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("topColor",topColor);
        intent.putExtra("bottomColor", bottomColor);
        startActivity(intent);
    }
}