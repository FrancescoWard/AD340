package com.example.wfrs06.hw4;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void canEnterFieldsAndLogin()
    {
        onView(withId(R.id.txtName)).perform(typeText("Hello World"));

        onView(withId(R.id.txtEmail)).perform(typeText("foo@bar.com"));

        onView(withId(R.id.txtUname)).perform(typeText("baz"));

        onView(withId(R.id.txtOccupation)).perform(typeText("Student"));

        onView(withId(R.id.cal)).perform(PickerActions.setDate(2000, 1, 1));

        onView(withId(R.id.btnLogin)).perform(scrollTo(),click());
    }

    @Test
    public void canEnterFieldsAndCannotLogin()
    {
        onView(withId(R.id.txtName)).perform(typeText("Hello World"));

        onView(withId(R.id.txtEmail)).perform(typeText("foo@bar.com"));

        onView(withId(R.id.txtUname)).perform(typeText("baz"));

        onView(withId(R.id.txtOccupation)).perform(typeText("Student"));

        onView(withId(R.id.cal)).perform(PickerActions.setDate(2005, 1, 1));

        onView(withId(R.id.btnLogin)).perform(scrollTo(),click());
    }

}