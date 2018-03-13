package ca.csf.mobile1.tp2.view.dialog;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.csf.mobile1.tp2.R;
import ca.csf.mobile1.tp2.activity.MainActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ca.csf.mobile1.tp2.test.KeyPickerDialogActions.ok;
import static ca.csf.mobile1.tp2.test.KeyPickerDialogActions.setKey;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class KeyPickerDialogTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private KeyPickerDialog.OnKeySelectedListener onKeySelectedListener;

    @Before
    public void before() {
        onKeySelectedListener = mock(KeyPickerDialog.OnKeySelectedListener.class);
    }

    @Test
    public void canOpenKeyPickerDialog() throws Throwable {
        onView(withId(R.id.keyPickerDialog)).check(doesNotExist());

        openKeyPickerDialog(0);

        onView(withId(R.id.keyPickerDialog)).check(matches(isDisplayed()));
    }

    @Test
    public void canPerformOkOnKeyPickerDialog() throws Throwable {
        onView(withId(R.id.keyPickerDialog)).check(doesNotExist());

        openKeyPickerDialog(0);
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.keyPickerDialog)).check(doesNotExist());
        verify(onKeySelectedListener).onKeySelected(anyInt());
    }

    @Test
    public void canPerformCancelOnKeyPickerDialog() throws Throwable {
        onView(withId(R.id.keyPickerDialog)).check(doesNotExist());

        openKeyPickerDialog(0);
        onView(withId(android.R.id.button2)).perform(click());

        onView(withId(R.id.keyPickerDialog)).check(doesNotExist());
        verify(onKeySelectedListener).onKeySelectionCancelled();
    }

    @Test
    public void canSetKeyOnKeyPickerDialog() throws Throwable {
        onView(withId(R.id.keyPickerDialog)).check(doesNotExist());

        openKeyPickerDialog(15);
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.keyPickerDialog)).check(doesNotExist());
        verify(onKeySelectedListener).onKeySelected(eq(15));
    }

    private void openKeyPickerDialog(final int key) throws Throwable {
        activityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KeyPickerDialog.show(activityRule.getActivity(), key, 5, onKeySelectedListener);
            }
        });
    }

}