package com.example.wfrs06.hw3b;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class Main2ActivityTest {

    @Rule
    public ActivityTestRule<Main2Activity> activityTestRule
            = new ActivityTestRule<Main2Activity>(Main2Activity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent testIntent = new Intent();
            testIntent.putExtra("name", "Hello World");
            testIntent.putExtra("email","foo@bar.com");
            testIntent.putExtra("uname","baz");
            testIntent.putExtra("occupation","student");
            return testIntent;
        }
    };

    @Test
    public void setsRightMessageBasedOnIntentExtra() {
        onView(withId(R.id.loginName))
                .check(matches(withText("Welcome baz")));

    }

    @Test
    public void setsRightDefaultImage()
    {
        onView(withId(R.id.img))
                .check(matches(isDisplayed()));
    }

    @Test
    public void canClickUploadImageButton()
    {
        onView(withId(R.id.btnUpload)).perform(scrollTo(),click());
    }
}