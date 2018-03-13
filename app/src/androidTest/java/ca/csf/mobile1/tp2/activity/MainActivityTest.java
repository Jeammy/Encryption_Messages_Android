package ca.csf.mobile1.tp2.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.csf.mobile1.tp2.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
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

    /**
     * User story #1
     */
    @Test
    public void canChangeKeyId() {
        show();

        openKeyPickerDialog();
        inputKeyInPickerDialog(11977);
        closeKeyPickerDialog();

        checkKeyIs(11977);
    }

    /**
     * User story #3
     */
    @Test
    public void canEncryptEmptyData() {
        show();
        setKeyTo(11977);
        performEncrypt("");
    }

    @Test
    public void canEncryptData() {
        show();
        setKeyTo(23942);
        performEncrypt("Hello world");
        onView(withId(R.id.textViewResult)).check(matches(withText("uK..cmPcb.x")));
    }

    /**
     * User story #4
     */
    @Test
    public void canDecryptData() {
        show();
        setKeyTo(23942);
        performDecrypt("uK..cmPcb.x");
        onView(withId(R.id.textViewResult)).check(matches(withText("Hello world")));
    }

    /*
     * User story #5
     */
    //Dans la fonction checkKeyIs, on va chercher la clé qui est affichée a l'écran donc,
    //on y a bien accès en un clin d'oeil

    /**
     * User story #6
     */
    @Test
    public void cantCopyEmptyClipboard() {
        show();
        Looper.prepare();
        clearClipboard();
        closeSoftKeyboard();
        onView(withId(R.id.imageButtonCopy)).perform(ViewActions.click());
        onView(withId(R.id.editText)).check(matches(withText(contentOfClipboard())));
    }

    @Test
    public void canCopyToClipboard() {
        show();
        Looper.prepare();
        setKeyTo(23942);
        performEncrypt("something");
        onView(withId(R.id.imageButtonCopy)).perform(ViewActions.click());
        onView(withId(R.id.textViewResult)).check(matches(withText(contentOfClipboard())));
    }

    /**
     * User story #7
     */
    @Test
    public void canSendTextToAppToDecrypt(){
        String text = "uK..cmPcb.x";
        show(text);
        inputKeyInPickerDialog(23942);
        closeKeyPickerDialog();
        onView(withId(R.id.buttonDecrypt)).perform(click());
        onView(withId(R.id.textViewResult)).check(matches(withText("Hello world")));
    }

    @Test
    public void canSendTextToAppToEncrypt(){
        String text = "Hello world";
        show(text);
        inputKeyInPickerDialog(23942);
        closeKeyPickerDialog();
        onView(withId(R.id.buttonEncrypt)).perform(click());
        onView(withId(R.id.textViewResult)).check(matches(withText("uK..cmPcb.x")));
    }

    /**
     * User story #8
     */
    @Test
    public void canChooseKeyWhenSendingTextToApp(){
        String text = "Hello world";
        show(text);
        inputKeyInPickerDialog(23942);
        closeKeyPickerDialog();
        checkKeyIs(23942);
    }

    /**
     * User story #10
     */
    @Test
    public void cantInputUnsupportedCharacters() {
        show();
        setKeyTo(23942);
        performEncrypt("this doesn't look like anything to me");
        onView(withId(R.id.textViewResult)).check(matches(withText("nozDmxcKDFnm.ccGm.zGKmyFJnozFhmncmUK")));
    }

    @Test
    public void cantInputUnsupportedCharacters2() {
        show();
        setKeyTo(23942);
        performEncrypt("'^^%&()$/!><;:,-+=*?#|");
        onView(withId(R.id.textViewResult)).check(matches(withText("")));
    }

    /**
     * Tests de changement d'orientation.
     */
    @Test
    public void savesEditTextWhenSwitchingOrientation(){
        show();
        setKeyTo(23942);
        onView(withId(R.id.editText)).perform(typeText("something"));
        setOrientationPortrait();
        setOrientationLandscape();
        onView(withId(R.id.editText)).check(matches(withText("something")));
    }

    @Test
    public void savesResultWhenSwitchingOrientation(){
        show();
        setKeyTo(23942);
        performEncrypt("sum ting wong");
        setOrientationPortrait();
        setOrientationLandscape();
        onView(withId(R.id.textViewResult)).check(matches(withText("DNUmnzFhmPcFh")));
    }

    @Test
    public void savesKeyWhenSwitchingOrientation(){
        show();
        setKeyTo(23942);
        setOrientationPortrait();
        setOrientationLandscape();
        checkKeyIs(23942);
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
        onView(withId(R.id.floatingActionButtonSelectKey)).perform(click());
    }

    private void inputKeyInPickerDialog(final int key) {
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
        String keyText = activityRule.getActivity().getResources().getString(R.string.text_view_key);
        onView(withId(R.id.textViewKey)).check(matches(withText(String.format(keyText, key))));
    }

    private String contentOfClipboard() {
        ClipboardManager clipboard = (ClipboardManager) activityRule.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        return clipboard.getPrimaryClip().getItemAt(0).getText().toString();
    }

    private void clearClipboard() {
        ClipboardManager clipboard = (ClipboardManager) activityRule.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText("");
    }

    private void performEncrypt(String stringToEncrypt){
        onView(withId(R.id.editText)).perform(typeText(stringToEncrypt));
        onView(withId(R.id.buttonEncrypt)).perform(click());
    }

    private void performDecrypt(String stringToDecrypt) {
        onView(withId(R.id.editText)).perform(typeText(stringToDecrypt));
        onView(withId(R.id.buttonDecrypt)).perform(click());
    }
}
