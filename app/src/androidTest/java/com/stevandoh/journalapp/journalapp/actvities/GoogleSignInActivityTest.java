package com.stevandoh.journalapp.journalapp.actvities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.stevandoh.journalapp.journalapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.junit.Assert.*;

public class GoogleSignInActivityTest {

    @Rule
    public ActivityTestRule<GoogleSignInActivity> activityTestRule =
            new ActivityTestRule<>(
                    GoogleSignInActivity.class, true, false
            );

    @Test
    public void ensureButtonIsClickableAndEnabled() {
        activityTestRule.launchActivity(null);

        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button)).check(matches(isEnabled()));

    }

}