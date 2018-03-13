package ca.csf.mobile1.tp2.model;

/**
 * Created by Jeammy on 29/03/17.
 */

/**
 * S'occupe de substituer les lettre de nôtre string.
 */
public class ComputeEncryption {
    private final char[] alphabet;
    private final char[] substitution;
    private final String text;

    public ComputeEncryption(final char[] alphabet,final char[] substitution,final String text){
        this.alphabet = alphabet;
        this.substitution = substitution;
        this.text = text;
    }

    public String encrypt(){
        String encryptedString = "";
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < alphabet.length; j++) {
                if(alphabet[j] == text.charAt(i)){
                    encryptedString += substitution[j];
                }
            }
        }
        return encryptedString;
    }

    public String decrypt(){
        String decryptedString = "";
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < substitution.length; j++) {
                if (substitution[j] == text.charAt(i)) {
                    decryptedString += alphabet[j];
                }
            }
        }
        return decryptedString;
    }
}
