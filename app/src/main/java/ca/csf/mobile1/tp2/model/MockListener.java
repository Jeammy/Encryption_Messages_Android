package ca.csf.mobile1.tp2.model;

/**
 * Created by Jeammy on 06/04/17.
 */

/**
 * Mock destiné au tests de AsyncTaskEncryptData.
 */
public class MockListener implements AsyncTaskEncryptData.IListener {
    private String response = null;
    private Exception exception = null;
    private boolean errorIsNotSuccesful;
    private boolean errorIOException;

    public MockListener() {
        this.response = "";
        this.exception = null;
        this.errorIsNotSuccesful = false;
        this.errorIOException = false;
    }
    @Override
    public void onResponse(final String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public void onException(Exception e) {
        this.exception = e;
    }

    public Exception getException(){
        return exception;
    }

    @Override
    public void errorIsNotSuccesful() {
        errorIsNotSuccesful = true;
    }

    public boolean isErrorIsNotSuccesful(){
        return this.errorIsNotSuccesful;
    }

    @Override
    public void errorIOException() {
        errorIOException = true;
    }

    public boolean isErrorIOException(){
        return this.errorIOException;
    }
}
