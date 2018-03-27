package com.chowpals.chowmein;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import com.amazonaws.mobile.auth.core.IdentityManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import helpers.UserHelper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchFromMainActivityNoLoginTest {

    private static final SimpleDateFormat ISO_DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void searchFromMainActivityNoLoginTest() {

        if (UserHelper.isUserSignedIn())
            IdentityManager.getDefaultIdentityManager().signOut();

        sleep();

        ViewInteraction appCompatImageView = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageView")), withContentDescription("Search"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withId(R.id.chowsSearchViewMain),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        sleep();

        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("Chowtestname"), closeSoftKeyboard());

        sleep();

        DataInteraction textView = onData(anything())
                .inAdapterView(allOf(withId(R.id.searchChowListViewMain),
                        childAtPosition(
                                withId(R.id.searchChowConstraintView),
                                2)))
                .atPosition(0);
        textView.perform(click());

        sleep();

        Date curdate = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(curdate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE) + 2;

        int[] validDate = validateDate(day, month, year);

        String date = getDate(validDate[0], validDate[1], validDate[2]);

        sleep();

        onView(allOf(withId(R.id.chowInfoTextView), withText("Chow name: Chowtestname\nCategory: Chinese\nMeet Location: Chowtestloc\nMeet Time: " + date + "\nAdditional Notes: Chowtestdesc\nContact Name: postTester")));

        IdentityManager.getDefaultIdentityManager().signOut();
    }

    /**************sleep
     * This function is used between step in the test class. This prevents the test from trying to assert and click on things that have not yet loaded,
     * thereby ensuring the accuracy of the tests.
     */
    private static void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int[] validateDate(int dayParm, int monthParm, int yearParm) {
        int day = dayParm;
        int month = monthParm;
        int year = yearParm;

        switch (month) {
            case 2:
                if (day > 28) {
                    day = 1;
                    month++;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (day > 30) {
                    day = 1;
                    month++;
                }
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
                if (day > 31) {
                    day = 1;
                    month++;
                }
                break;
            case 12:
                if (day > 31) {
                    day = 1;
                    month = 1;
                    year++;
                }
        }

        return new int[]{day, month, year};
    }

    private String getDate(int dayParm, int monthParm, int yearParm) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(yearParm, monthParm - 1, dayParm, 12, 0, 0);

        Date dateObj = calendar.getTime();
        return ISO_DATA_FORMAT.format(dateObj);
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
