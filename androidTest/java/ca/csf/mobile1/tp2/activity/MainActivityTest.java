package ca.csf.mobile1.tp2.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.csf.mobile1.tp2.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ca.csf.mobile1.tp2.test.KeyPickerDialogActions.ok;
import static ca.csf.mobile1.tp2.test.KeyPickerDialogActions.setKey;
import static ca.csf.mobile1.tp2.test.OrientationChangeAction.orientationLandscape;
import static ca.csf.mobile1.tp2.test.OrientationChangeAction.orientationPortrait;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void canChangeKeyId() {
        show();

        openKeyPickerDialog();
        inputKeyInPickerDialog(11977);
        closeKeyPickerDialog();

        checkKeyIs(11977);
    }

    private void show() {
        activityRule.launchActivity(null);
    }

    private void show(String textToDecrypt) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textToDecrypt);

        activityRule.launchActivity(intent);
    }

    private void openKeyPickerDialog() {
        onView(withId(R.id.selectKeyButton)).perform(click());
    }

    private void inputKeyInPickerDialog(int key) {
        onView(withId(R.id.keyPickerDialog)).perform(setKey(key));
    }

    private void closeKeyPickerDialog() {
        onView(withId(R.id.keyPickerDialog)).perform(ok());
    }

    private void setOrientationLandscape() {
        onView(withId(R.id.rootView)).perform(orientationLandscape());
    }

    private void setOrientationPortrait() {
        onView(withId(R.id.rootView)).perform(orientationPortrait());
    }

    private void setKeyTo(int key) {
        openKeyPickerDialog();
        inputKeyInPickerDialog(key);
        closeKeyPickerDialog();
    }

    private void checkKeyIs(int key) {
        String keyText = activityRule.getActivity().getResources().getString(R.string.text_current_key);
        onView(withId(R.id.currentKeyTextView)).check(matches(withText(String.format(keyText, String.valueOf(key)))));
    }

    private String contentOfClipboard() {
        ClipboardManager clipboard = (ClipboardManager) activityRule.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        return clipboard.getPrimaryClip().getItemAt(0).getText().toString();
    }

}
