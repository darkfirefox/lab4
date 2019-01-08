package com.example.pavel.bookkeeping;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RootActivityTest {
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.example.android.testing.uiautomator.BasicSample";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;



    @Rule
    public ActivityTestRule<RootActivity> mActivityTestRule = new ActivityTestRule<>(RootActivity.class);


    @Test
    public void addRemoveAccount() {
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText(R.string.newAccount)).perform(click());
        onView(withId(R.id.accountNameEdit)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.accountBalanceEdit)).perform(typeText("500"),closeSoftKeyboard());
        onView(withId(R.id.accountDescEdit)).perform(typeText("desc"),closeSoftKeyboard());
        onView(withId(R.id.acceptAccount)).perform(click());

        onView(withId(R.id.recycleView)).perform(actionOnItemAtPosition(0,longClick()));
        onView(withText(R.string.delete)).perform(click());
    }

    @Test
    public void addRemoveOperation() {
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText(R.string.newAccount)).perform(click());
        onView(withId(R.id.accountNameEdit)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.accountBalanceEdit)).perform(typeText("500"),closeSoftKeyboard());
        onView(withId(R.id.accountDescEdit)).perform(typeText("desc"),closeSoftKeyboard());
        onView(withId(R.id.acceptAccount)).perform(click());

        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText(R.string.newOperation)).perform(click());
        onView(withId(R.id.operationAccountFromSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationAccountToSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationTypeSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationValueEdit)).perform(typeText("500"),closeSoftKeyboard());
        onView(withId(R.id.operationCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationDescEdit)).perform(typeText("desc"),closeSoftKeyboard());
        onView(withId(R.id.accept)).perform(click());

        onView(withText(R.string.arrivalTab)).perform(click());

        onView(withId(R.id.recycleView)).perform(actionOnItemAtPosition(0,longClick()));
        onView(withText(R.string.delete)).perform(click());
    }

    @Test
    public void searchOperation() {
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText(R.string.newAccount)).perform(click());
        onView(withId(R.id.accountNameEdit)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.accountBalanceEdit)).perform(typeText("500"),closeSoftKeyboard());
        onView(withId(R.id.accountDescEdit)).perform(typeText("desc"),closeSoftKeyboard());
        onView(withId(R.id.acceptAccount)).perform(click());

        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText(R.string.newOperation)).perform(click());
        onView(withId(R.id.operationAccountFromSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationAccountToSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationTypeSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationValueEdit)).perform(typeText("500"),closeSoftKeyboard());
        onView(withId(R.id.operationCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationDescEdit)).perform(typeText("desc"),closeSoftKeyboard());
        onView(withId(R.id.accept)).perform(click());

        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText(R.string.newOperation)).perform(click());
        onView(withId(R.id.operationAccountFromSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationAccountToSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationTypeSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationValueEdit)).perform(typeText("500"),closeSoftKeyboard());
        onView(withId(R.id.operationCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());
        onView(withId(R.id.operationDescEdit)).perform(typeText("tyt"),closeSoftKeyboard());
        onView(withId(R.id.accept)).perform(click());

        onView(withText(R.string.arrivalTab)).perform(click());

        onView(withContentDescription("Поиск")).perform(click());
        onView(withId(R.id.searchView)).perform(typeText("tyt"));
    }

    @Test
    public void rotate() {
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Test
    public void home(){
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, CoreMatchers.notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
