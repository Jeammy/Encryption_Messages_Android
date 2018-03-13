package ca.csf.mobile1.tp2.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeammy on 04/04/17.
 */
public class ComputeEncryptionTest {

    @Test
    public void encryptEmptyString() throws Exception {
        ComputeEncryption computeEncryptionEmptyString = new ComputeEncryption("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .".toCharArray(),
                "xTHIaqZOXVzvSMRLhmB ldesKNnQrpDoigWb.GUEwCfPkAJtFcyujY".toCharArray(),
                "");

        assertEquals("", computeEncryptionEmptyString.encrypt());
    }

    @Test
    public void encrypt() throws Exception {
        ComputeEncryption computeEncryptionEmptyString = new ComputeEncryption("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .".toCharArray(),
                "xTHIaqZOXVzvSMRLhmB ldesKNnQrpDoigWb.GUEwCfPkAJtFcyujY".toCharArray(),
                "Hello world");
        assertEquals("gavvRjeRmvI", computeEncryptionEmptyString.encrypt());
    }

    @Test
    public void decrypt() throws Exception {
        ComputeEncryption computeEncryptionEmptyString = new ComputeEncryption("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .".toCharArray(),
                "xTHIaqZOXVzvSMRLhmB ldesKNnQrpDoigWb.GUEwCfPkAJtFcyujY".toCharArray(),
                "gavvRjeRmvI");
        assertEquals("Hello world", computeEncryptionEmptyString.decrypt());
    }

}