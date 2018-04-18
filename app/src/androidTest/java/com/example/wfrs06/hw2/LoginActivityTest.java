package com.example.wfrs06.hw2;

import android.graphics.Picture;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Month;
import java.util.Calendar;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    //TODO make multiple test methods for each test individually
    @Rule
    public ActivityTestRule<LoginActivity> testRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> testRule2 = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void rejectedLogin()
    {
        onView(withId(R.id.name))
                .perform(replaceText("HelloWorld"));

        onView(withId(R.id.email))
                .perform(replaceText("foo@bar.com"));
        onView(withId(R.id.username))
                .perform(replaceText("baz"));

        //should reject login
        onView(withId(R.id.email_sign_in_button)).perform(scrollTo());

    onView(withId(R.id.cal)).perform(PickerActions.setDate(Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));

        onView(withId(R.id.email_sign_in_button)).perform(scrollTo());
        onView(withId(R.id.email_sign_in_button)).perform(click());

    }

    @Test
    public void successfulLogin()
    {
        onView(withId(R.id.name))
                .perform(replaceText("HelloWorld"));

        onView(withId(R.id.email))
                .perform(replaceText("foo@bar.com"));
        onView(withId(R.id.username))
                .perform(replaceText("baz"));

        //should allow login
        onView(withId(R.id.email_sign_in_button)).perform(scrollTo());

        onView(withId(R.id.cal)).perform(PickerActions.setDate(2000, Calendar.FEBRUARY, 16));

        onView(withId(R.id.email_sign_in_button)).perform(scrollTo(),click());


        onView(withId(R.id.success))
                .check(matches(withText("Thanks for Signing up baz")));

        onView(withId(R.id.btnBack)).perform(click());

        onView(withId(R.id.name))
                .check(matches(withText("")));

        onView(withId(R.id.email))
                .check(matches(withText("")));

        onView(withId(R.id.username))
                .check(matches(withText("")));

        onView(withId(R.id.age))
                .check(matches(withText("")));

    }
}