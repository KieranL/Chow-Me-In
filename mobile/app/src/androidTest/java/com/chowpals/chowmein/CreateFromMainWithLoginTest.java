package com.chowpals.chowmein;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import helpers.UserHelper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateFromMainWithLoginTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createFromMainWithLoginTest() {

        sleep();

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        sleep();

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        sleep();

        ViewInteraction editText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("com.amazonaws.mobile.auth.userpools.FormView")),
                                0),
                        1),
                        isDisplayed()));
        editText.perform(click());

        sleep();

        ViewInteraction editText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("com.amazonaws.mobile.auth.userpools.FormView")),
                                0),
                        1),
                        isDisplayed()));
        editText2.perform(replaceText("postTester"), closeSoftKeyboard());

        sleep();

        ViewInteraction editText3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("com.amazonaws.mobile.auth.userpools.FormEditText")),
                                1),
                        0),
                        isDisplayed()));
        editText3.perform(replaceText("password1"), closeSoftKeyboard());

        sleep();

        ViewInteraction button = onView(
                allOf(withText("Sign In"),
                        childAtPosition(
                                allOf(withId(R.id.sign_in_view),
                                        childAtPosition(
                                                withClassName(is("com.chowpals.chowmein.login.LoginView")),
                                                1)),
                                1),
                        isDisplayed()));
        button.perform(click());

        sleep();

        assertEquals(true,UserHelper.isUserSignedIn());

        sleep();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.createChowButton),
                        childAtPosition(
                                allOf(withId(R.id.createChowConstraintView),
                                        childAtPosition(
                                                withId(R.id.createChowCardView),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        sleep();

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.chowPostNameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), click());

        sleep();

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.chowPostNameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("Fries"), closeSoftKeyboard());

        sleep();

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.chowPostDescriptionEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("Large"), closeSoftKeyboard());

        sleep();

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.chowPostLocationEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText4.perform(scrollTo(), replaceText("McDonald's"), closeSoftKeyboard());

        sleep();

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.categorySpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatSpinner2.perform(scrollTo(), click());

        sleep();

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatTextView.perform(click());

        sleep();

        onView(withId(Matchers.equalTo(R.id.chowPostDatePicker))).perform(PickerActions.setDate(2018, 03, 10));

        sleep();

        onView(withText("Verify Chow")).perform(ViewActions.scrollTo());

        sleep();

        onView(withId(Matchers.equalTo(R.id.chowPostTimePicker))).perform(PickerActions.setTime(12, 0));

        sleep();
        sleep();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.createChowButton), withText("Verify Chow"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatButton2.perform(scrollTo(), click());

        sleep();

        onView(allOf(withId(R.id.chowInfoTextView), withText("Chow name: Fries\nCategory: American\nMeet Location: McDonald's\nMeet Time: 2018-03-10T17:25:11\nAdditional Notes: Large\nContact Name: postTester")));

        sleep();

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.submitChowButton), withText("Submit Chow"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_view),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton3.perform(click());

        sleep();

        DataInteraction textView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.searchChowListViewMain),
                        childAtPosition(
                                withId(R.id.searchChowConstraintView),
                                2)))
                .atPosition(1);
        textView2.perform(click());

        sleep();

        onView(allOf(withId(R.id.chowInfoTextView), withText("Chow name: Fries\nCategory: American\nMeet Location: McDonald's\nMeet Time: 2018-03-10T17:25:11\nAdditional Notes: Large\nContact Name: postTester")));

    }

    /**************sleep
     * This function is used between step in the test class. This prevents the test from trying to assert and click on things that have not yet loaded,
     * thereby ensuring the accuracy of the tests.
     */
    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
