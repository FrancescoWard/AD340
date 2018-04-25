package com.example.wfrs06.hw3b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    EditText mNameView;
    EditText mEmailView;
    EditText mUnameView;
    EditText mOccuView;
    EditText mAgeView;
    DatePicker mCalView;
    Button mLoginView;
    GregorianCalendar mDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameView = (EditText)findViewById(R.id.txtName);
        mEmailView = (EditText)findViewById(R.id.txtEmail);
        mUnameView = (EditText)findViewById(R.id.txtUname);
        mOccuView = (EditText)findViewById(R.id.txtOccupation);
        mAgeView = (EditText)findViewById(R.id.txtAge);
        mCalView = findViewById(R.id.cal);
        mLoginView = findViewById(R.id.btnLogin);

        //initialize calendar
        mCalView.init(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mDob = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                        //set the age field
                        try
                        {
                            mAgeView.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - mDob.get(Calendar.YEAR)));
                        }
                        catch (Exception e)
                        {

                        }
                    }
                });

        mLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }


    protected void attemptLogin()
    {
        if (validateName() && validateEmail() && validateAge())
        {
            //Launch new activity
            Intent login = new Intent(this, Main2Activity.class);
            login.putExtra("uname", mUnameView.getText().toString());
            login.putExtra("occupation", mOccuView.getText().toString());
            login.putExtra("age", mAgeView.getText().toString());
            startActivity(login);

        }
    }

    protected boolean validateName()
    {
        String name = "";
        boolean valid = false;
        try
        {
            name = mNameView.getText().toString();
            if (0 == name.trim().length())
            {
                valid = false;
                mNameView.setError("Please enter a valid name");
            }
            else
            {
                valid = true;
            }
        }
        catch (NullPointerException ex)
        {

        }
        return valid;
    }

    protected boolean validateEmail()
    {
        String email = "";
        boolean valid = false;
        try
        {
            email = mEmailView.getText().toString();
            valid = email.contains("@");
            if (!valid)
            {
                mEmailView.setError("Please enter a valid email");
            }
        }
        catch (NullPointerException ex)
        {

        }
        return valid;
    }

    protected boolean validateAge()
    {
        boolean isAdult = false;
        try
        {
            long ageYears = Calendar.getInstance().get(Calendar.YEAR) - mDob.get(Calendar.YEAR);
            long ageMonths = Calendar.getInstance().get(Calendar.MONTH) - mDob.get(Calendar.MONTH);
            long ageDays = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - mDob.get(Calendar.DAY_OF_MONTH);

            if (ageYears > 18) {
                isAdult = true;
            } else if (ageYears == 18) {
                if (ageMonths > 0) {
                    isAdult = true;
                } else if (ageMonths == 0) {
                    if (ageDays >= 0) {
                        isAdult = true;
                    }
                }
            }
        }
        catch (NullPointerException ex)
        {
            isAdult = false;
        }

        if(!isAdult)
        {
            Toast.makeText(this,"Sorry, not 18 years old",Toast.LENGTH_LONG).show();
        }

        return isAdult;
    }
}
