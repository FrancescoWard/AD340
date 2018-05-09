package com.example.wfrs06.hw5;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityProfile extends AppCompatActivity {

    Bitmap mImg;
    ImageView mImgView;
    TextView mLoginName;
    TextView mLoginAge;
    TextView mOccupation;
    EditText mTxtDesc;
    Button mBtnUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mImgView = findViewById(R.id.img);
        mLoginName = (TextView)findViewById(R.id.loginName);
        mLoginAge = (TextView)findViewById(R.id.loginAge);
        mOccupation = (TextView)findViewById(R.id.loginOccupation);
        mTxtDesc = (EditText)findViewById(R.id.txtDesc);
        mBtnUpload = (Button)findViewById(R.id.btnUpload);
    }
}
