package com.example.wfrs06.hw5;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;




import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;


import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import android.support.test.espresso.intent.Intents;
import android.widget.CheckBox;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static org.hamcrest.core.AllOf.allOf;

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
    public void setsRightDefaultImage()
    {
        onView(withId(R.id.img))
                .check(matches(isDisplayed()));
    }

    @Test
    public void canSwipeLeft()
    {
        onView(withId(R.id.viewpager))
                .perform(swipeLeft());

        onView(withId(R.id.viewpager))
                .perform(swipeLeft());

    }

    @Test
    public void canSwipeRight()
    {
        Main2Activity ac = activityTestRule.getActivity();

        onView(withId(R.id.viewpager))
                .perform(swipeRight());

        onView(withId(R.id.viewpager))
                .perform(swipeRight());

    }

    @Test
    public void testToast1() {
        //#1
        Main2Activity ac = activityTestRule.getActivity();

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked Palais Garnie")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
    }

    @Test
    public void testToast2() {
        //#2
        Main2Activity ac = activityTestRule.getActivity();

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, myMatcher.clickChildViewWithId(R.id.favorite_button)));


        onView(withText("You liked Piazza del Duomo")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
    }

    @Test
    public void testToast3() {
        //#3
        Main2Activity ac = activityTestRule.getActivity();

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(2, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked Manhattan")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
    }

    @Test    //#4
    public void testToast4() {
        Main2Activity ac = activityTestRule.getActivity();

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(3, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked Senso-ji")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
    }

    @Test
    public void testToast5() {
        //#5
        Main2Activity ac = activityTestRule.getActivity();

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(4, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked Sultan Ahmed Mosque")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
    }

    @Test
    public void testToast6() {
        //#6
        Main2Activity ac = activityTestRule.getActivity();

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(5, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked Table Mountain")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
    }

    @Test
    public void testSettings()
    {
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.daily_matches_reminder_time)).perform(scrollTo(), PickerActions.setTime(6,0));

        onView(withId(R.id.max_distance_search)).perform(scrollTo(), typeText("10"));

        onView(withId(R.id.age_range_from)).perform(scrollTo(), typeText("30"));
        onView(withId(R.id.age_range_to)).perform(scrollTo(), typeText("35"));
    }
}