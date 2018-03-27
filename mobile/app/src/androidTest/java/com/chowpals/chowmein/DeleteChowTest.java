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

import helpers.UserHelper;
import objects.TestNavBarLoggedIn;
import objects.TestNavBarLoggedOut;

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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeleteChowTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void deleteChowTest() {

        if (UserHelper.isUserSignedIn())
            IdentityManager.getDefaultIdentityManager().signOut();

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
                        TestNavBarLoggedOut.LOGIN.ordinal() + 1),
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

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        sleep();

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        TestNavBarLoggedIn.MY_CHOWS.ordinal() + 1),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

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

        DataInteraction textView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.searchChowsCreatedByUserListViewMain),
                        childAtPosition(
                                withId(R.id.searchChowsCreatedByUserConstraintView),
                                2)))
                .atPosition(0);
        textView2.perform(click());

        sleep();

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_delete_chow), withContentDescription("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        sleep();

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        sleep();

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        sleep();

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        TestNavBarLoggedIn.LOGOUT.ordinal() + 1),
                        isDisplayed()));
        navigationMenuItemView3.perform(click());

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
