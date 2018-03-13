package ca.csf.mobile1.tp2.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

import ca.csf.mobile1.tp2.R;
import ca.csf.mobile1.tp2.model.AsyncTaskEncryptData;
import ca.csf.mobile1.tp2.model.SubstitutionCypherKey;
import ca.csf.mobile1.tp2.view.dialog.KeyPickerDialog;
import ca.csf.mobile1.tp2.view.input.filter.CharactersInputFilter;

public class MainActivity extends AppCompatActivity implements AsyncTaskEncryptData.IListener, KeyPickerDialog.OnKeySelectedListener{

    private int key;
    private EditText inputEditText;
    private TextView textViewResult;
    private TextView textViewKey;
    private View rootView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random randomGenerator = new Random();
        key = randomGenerator.nextInt(99999);

        CharactersInputFilter inputFilter = new CharactersInputFilter(
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .".toCharArray());

        rootView = findViewById(R.id.rootView);
        inputEditText = (EditText) findViewById(R.id.editText);
        inputEditText.setFilters(new InputFilter[]{inputFilter});
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewKey = (TextView) findViewById(R.id.textViewKey);
        textViewKey.setText(String.format(getString(R.string.text_view_key),key));
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if("text/plain".equals(intent.getType())){
            String message = intent.getStringExtra(Intent.EXTRA_TEXT);
            putTextInClipboard(message);
            inputEditText.setText(message);
            onClickFloatingActionButtonSelectKey(rootView);
        }

        if (savedInstanceState != null){
            key = savedInstanceState.getInt("KEY");
            textViewKey.setText(String.format(getString(R.string.text_view_key),key));
            inputEditText.setText(savedInstanceState.getString("INPUTTEXT"));
            textViewResult.setText(savedInstanceState.getString("RESULTTEXT"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt("KEY",key);
        outState.putString("INPUTTEXT",inputEditText.getText().toString());
        outState.putString("RESULTTEXT",textViewResult.getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void putTextInClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(getResources().getString(R.string.clipboard_encrypted_text), text));
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void onClickButtonCopy(View view) {
        if (!(textViewResult.getText().toString().equals(getString(R.string.text_view_result))) ) {
            putTextInClipboard(textViewResult.getText().toString());
        }
    }

    private void getEncryptionSubstitution(final String toEncrypt){
        progressBar.setVisibility(View.VISIBLE);
        AsyncTaskEncryptData obtainSubstitutionWithKey = new AsyncTaskEncryptData();
        obtainSubstitutionWithKey.addListener(this);
        obtainSubstitutionWithKey.execute(Integer.toString(key),inputEditText.getText().toString(),toEncrypt);
    }

    @Override
    public void onResponse(final String response) {
        textViewResult.setText(response);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onException(Exception e) {
        progressBar.setVisibility(View.INVISIBLE);
        Snackbar.make(rootView,R.string.no_internet_connection,Snackbar.LENGTH_LONG).show();
        proposeWifi();
    }

    @Override
    public void errorIsNotSuccesful(){
        Snackbar.make(rootView, R.string.server_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void errorIOException(){
        Snackbar.make(rootView, R.string.connexion_error, Snackbar.LENGTH_LONG).show();
    }

    //Pour fermer le clavier http://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard/34972848
    public void onClickButtonEncrypt(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        try{
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        getEncryptionSubstitution("encrypt");
    }

    public void onClickButtonDecrypt(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        try{
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        getEncryptionSubstitution("decrypt");
    }

    public void onClickFloatingActionButtonSelectKey(View view) {
        KeyPickerDialog.show(this, key, 5,this);
    }

    @Override
    public void onKeySelected(final int key) {
        this.key = key;
        textViewKey.setText(String.format(getString(R.string.text_view_key),key));
    }

    @Override
    public void onKeySelectionCancelled() {

    }

    private void proposeWifi(){
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}
