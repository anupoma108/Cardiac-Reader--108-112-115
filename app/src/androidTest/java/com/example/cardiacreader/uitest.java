package com.example.cardiacreader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.openLinkWithText;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

//import static org.hamcrest.CoreMatchers.anything;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cardiacreader.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class uitest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testMain() {
        onView(withText("All Records")).check(matches(isDisplayed())); //Check the name on the screen
    }



    @Test
    public void bottomFragementAppear() {
        onView(withId(R.id.addTask)).perform(click()); //Click add button to add a record
        onView(withText("Add a Record")).check(matches(isDisplayed()));
    }

    @Test
    public void insertItem() {
        onView(withId(R.id.addTask)).perform(click()); //Click add button to add a record
        onView(withId(R.id.date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.time)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.systolic_pressure)).perform(ViewActions.typeText("120"),pressImeActionButton());
        onView(withId(R.id.date)).perform(ViewActions.swipeUp());
        onView(withId(R.id.diastolic_pressure)).perform(ViewActions.typeText("90"),pressImeActionButton());
        onView(withId(R.id.heart_rate)).perform(ViewActions.typeText("72"),pressImeActionButton());
        onView(withId(R.id.comment)).perform(ViewActions.typeText("Normal"));
        onView(withId(R.id.comment)).perform(pressImeActionButton());
        onView(withId(R.id.add)).perform(click());
    }

    @Test
    public void update() {
        onView(withId(R.id.options_t)).perform(click());
        onView(withText("Update")).perform(click());
        onView(withId(R.id.date)).perform(click());
        onView(withText("CANCEL")).perform(click());
        onView(withId(R.id.time)).perform(click());
        onView(withText("CANCEL")).perform(click());
        onView(withId(R.id.systolic_pressure)).perform(ViewActions.clearText(),ViewActions.typeText("120"),pressImeActionButton());
        onView(withId(R.id.date)).perform(ViewActions.swipeUp());
        onView(withId(R.id.diastolic_pressure)).perform(ViewActions.clearText(),ViewActions.typeText("30"),pressImeActionButton());
        onView(withId(R.id.heart_rate)).perform(ViewActions.clearText(),ViewActions.typeText("150"),pressImeActionButton());
        onView(withId(R.id.comment)).perform(ViewActions.clearText(),ViewActions.typeText("Alarming"));
        onView(withId(R.id.comment)).perform(pressImeActionButton());
        onView(withId(R.id.add)).perform(click());

    }

    @Test
    public void delete() {
        onView(withId(R.id.options_t)).perform(click());
        onView(withText("Delete")).perform(click());
        onView(withText("YES")).perform(click());
    }
}

