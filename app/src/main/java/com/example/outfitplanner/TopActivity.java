package com.example.outfitplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
    }

    public void chooseTop(View view){
        //start camera module

        //save the image

        //set the image in imageView
    }

    public void goToBottom(View view){
        Intent intent = new Intent(this, BottomActivity.class);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}