package ca.csf.mobile1.tp2.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jeammy on 06/04/17.
 */
public class SubstitutionCypherKeyTest {
    @Test
    public void canMixInKey() throws Exception {
        SubstitutionCypherKey substitutionCypherKey ;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(SubstitutionCypherKey.class, SubstitutionCypherKeyMixIn.class);
        substitutionCypherKey = objectMapper.readValue("{\"outputCharacters\":\"" +
                "yIWxKphozrG.UFcHsbDnNaPCJVBkgOlfMuEZXQStweRTLAidvqjYm \",\"key\":23942," +
                "\"inputCharacters\":\"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .\"}",
                SubstitutionCypherKey.class);

        assertEquals(23942,substitutionCypherKey.getKey());
    }

    @Test
    public void canMixInOutput() throws Exception {
        SubstitutionCypherKey substitutionCypherKey;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(SubstitutionCypherKey.class, SubstitutionCypherKeyMixIn.class);
        substitutionCypherKey = objectMapper.readValue("{\"outputCharacters\":\"" +
                        "yIWxKphozrG.UFcHsbDnNaPCJVBkgOlfMuEZXQStweRTLAidvqjYm \",\"key\":23942," +
                        "\"inputCharacters\":\"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .\"}",
                SubstitutionCypherKey.class);

        assertEquals("yIWxKphozrG.UFcHsbDnNaPCJVBkgOlfMuEZXQStweRTLAidvqjYm ",String.valueOf(substitutionCypherKey.getOutputCharacters()));
    }

    @Test
    public void canMixInInput() throws Exception {
        SubstitutionCypherKey substitutionCypherKey;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(SubstitutionCypherKey.class, SubstitutionCypherKeyMixIn.class);
        substitutionCypherKey = objectMapper.readValue("{\"outputCharacters\":\"" +
                        "yIWxKphozrG.UFcHsbDnNaPCJVBkgOlfMuEZXQStweRTLAidvqjYm \",\"key\":23942," +
                        "\"inputCharacters\":\"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .\"}",
                SubstitutionCypherKey.class);

        assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .",String.valueOf(substitutionCypherKey.getInputCharacters()));
    }
}