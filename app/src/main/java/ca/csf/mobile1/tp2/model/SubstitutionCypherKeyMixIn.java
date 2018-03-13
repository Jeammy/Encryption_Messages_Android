package ca.csf.mobile1.tp2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jeammy on 29/03/17.
 */

public abstract class SubstitutionCypherKeyMixIn extends SubstitutionCypherKey{
    @JsonCreator
    public SubstitutionCypherKeyMixIn(@JsonProperty("outputCharacters") char[] outputCharacters,
                                      @JsonProperty("key") int key,
                                      @JsonProperty("inputCharacters") char[] inputCharacters){
        super(key,inputCharacters, outputCharacters);
    }

    @JsonProperty("key")
    public abstract int getKey();

    @JsonProperty("inputCharacters")
    public abstract char[] getInputCharacters();

    @JsonProperty("outputCharacters")
    public abstract char[] getOutputCharacters();

}
