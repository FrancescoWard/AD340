package com.example.wfrs06.hw5;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;




import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;


import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

import static android.support.test.espresso.intent.Intents.intended;
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
/*
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
*/
    @Test
    public void testToast1() {
        //#1
        Main2Activity ac = activityTestRule.getActivity();

        onView(withText("Matches")).perform(click());
/*
        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.locationControllerNetwork)).perform(scrollTo(),click());

        ac.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try {
            Thread.sleep(10000);
        }
        catch (Exception e)
        {

        }

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked null")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
        */
    }

    @Test
    public void testToast2() {
        //#2
        Main2Activity ac = activityTestRule.getActivity();

        onView(withText("Matches")).perform(click());

        //onView(withId(R.id.viewpager)).perform(swipeLeft());
/*
        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, myMatcher.clickChildViewWithId(R.id.favorite_button)));


        onView(withText("You liked null")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
        */
    }

    @Test
    public void testToast3() {
        //#3
        Main2Activity ac = activityTestRule.getActivity();

        onView(withText("Matches")).perform(click());

        /*
        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(2, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked Cool Guy Mike")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
        */
    }

    @Test    //#4
    public void testToast4() {
        Main2Activity ac = activityTestRule.getActivity();

        onView(withText("Matches")).perform(click());



        /*
        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(3, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked null")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
        */
    }

    @Test
    public void testToast5() {
        //#5
        Main2Activity ac = activityTestRule.getActivity();

        onView(withText("Matches")).perform(click());

        /*
        onView(withId(R.id.viewpager)).perform(swipeLeft());


        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(4, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked Overachiever Alex")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
        */
    }

    @Test
    public void testToast6() {
        //#6
        Main2Activity ac = activityTestRule.getActivity();

        onView(withText("Matches")).perform(click());

        /*
        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(5, myMatcher.clickChildViewWithId(R.id.favorite_button)));

        onView(withText("You liked Hayden the Wrestler")).
                inRoot(withDecorView(not(ac.getWindow().getDecorView()))).
                check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }
        */
    }
}