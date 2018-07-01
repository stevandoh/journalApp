package com.stevandoh.journalapp.journalapp.actvities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.stevandoh.journalapp.journalapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(
                    MainActivity.class, true, false
            );

    @Test
    public void ensureButtonIsClickableAndEnabled() {
        activityTestRule.launchActivity(null);

        Espresso.onView(ViewMatchers.withId(R.id.fab_add)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.fab_add)).check(matches(isEnabled()));

    }


    @Test
    public void setClickHandler() {

    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onCreateOptionsMenu() {
    }

    @Test
    public void onOptionsItemSelected() {
    }
}