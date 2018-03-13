package ca.csf.mobile1.tp2.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeammy on 05/04/17.
 */
public class AsyncTaskEncryptDataTest {
    @Test
    public void asyncTaskEncryptTest() throws Exception {
        MockListener mockListener = new MockListener();

        AsyncTaskEncryptData obtainSubstitutionWithKey = new AsyncTaskEncryptData();
        obtainSubstitutionWithKey.addListener(mockListener);
        obtainSubstitutionWithKey.execute(Integer.toString(23942),"something","encrypt");

        assertEquals("DcUKnozFh",mockListener.getResponse());
    }

    @Test
    public void asyncTaskDecryptTest() throws Exception {
        MockListener mockListener = new MockListener();

        AsyncTaskEncryptData obtainSubstitutionWithKey = new AsyncTaskEncryptData();
        obtainSubstitutionWithKey.addListener(mockListener);
        obtainSubstitutionWithKey.execute(Integer.toString(23942),"DcUKnozFh","decrypt");

        assertEquals("something",mockListener.getResponse());
    }

    @Test
    public void asyncTaskWithNothing() throws Exception {
        MockListener mockListener = new MockListener();

        AsyncTaskEncryptData obtainSubstitutionWithKey = new AsyncTaskEncryptData();
        obtainSubstitutionWithKey.addListener(mockListener);
        obtainSubstitutionWithKey.execute(Integer.toString(23942),"","encrypt");

        assertEquals("", mockListener.getResponse());
    }
}