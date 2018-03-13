package ca.csf.mobile1.tp2.model;
/**
 * Created by Jeammy on 27/03/17.
 */

/**
 * Où est emmagasiné la clé de substitution et l'alphabet de substitution.
 */
public class SubstitutionCypherKey {
    private final int key;
    private final char[] inputCharacters;
    private char[] outputCharacters;

    public SubstitutionCypherKey(){
        this.key=0;
        this.inputCharacters = null;
        this.outputCharacters = null;
    }

    public SubstitutionCypherKey(int key, char[] inputCharacters, char[] outputCharacters){
        this.key = key;
        this.inputCharacters = inputCharacters;
        this.outputCharacters = outputCharacters;
    }

    public int getKey(){
        return key;
    }

    public char[] getInputCharacters(){
        return inputCharacters;
    }

    public char[] getOutputCharacters(){
        return outputCharacters;
    }


}
