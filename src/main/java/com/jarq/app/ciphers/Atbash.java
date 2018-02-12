package com.jarq.app.ciphers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Atbash extends AbstractCipher {

    public Atbash() {
        description = "is a particular type of monoalphabetic cipher formed by taking the alphabet\n" +
                "        (or abjad, syllabary, etc.) and mapping it to its reverse, so that the first letter\n" +
                "        becomes the last letter, the second letter becomes the second to last letter, and so on.";
        isKeyRequired = false;
        this.setName("Atbash");
    }

    protected String encrypt(String text) {
        int A_ASCII_INDEX = 65;
        int Z_ASCII_INDEX = 90;
        boolean isCharLower;
        char[] inputAr = text.toCharArray();
        char[] outputAr = new char[inputAr.length];
        int key;
            for(int i=0; i<inputAr.length; i++) {
                char character = inputAr[i];
                Pattern regex = Pattern.compile("[a-zA-Z]");
                Matcher matcher = regex.matcher(String.valueOf(character));
                if (matcher.find()) {
                    isCharLower = Character.isLowerCase(character);  // to set flag
                    character = Character.toUpperCase(character);  // to operate only on upper case chars
                    key = character - A_ASCII_INDEX;
                    character = (char) (Z_ASCII_INDEX - key);
                if (isCharLower) {
                character = Character.toLowerCase(character);
                }
            }
            outputAr[i] = character;
        }

        return new String(outputAr);
    }

    protected String decrypt(String text) {
        return encrypt(text);
    }
}
