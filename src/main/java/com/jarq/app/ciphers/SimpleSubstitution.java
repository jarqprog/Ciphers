package com.jarq.app.ciphers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public abstract class SimpleSubstitution extends AbstractCipher {

    private int key;

    public SimpleSubstitution() {
        isKeyRequired = true;
        description = "helper cipher";
    }

    public void setKey(int newKey) {
        key = newKey;
    }

    @Override
    public String getKeyInfo() {
        return "key should be positive integer number not greater than 24 ";
    }

    protected String encrypt(String text) {
        int A_ASCII_INDEX = 65;
        int Z_ASCII_INDEX = 90;
        int asciFactor;
        boolean isCharLower;
        char[] inputAr = text.toCharArray();
        char[] outputAr = new char[inputAr.length];
        for(int i=0; i<inputAr.length; i++) {
            char character = inputAr[i];
            Pattern regex = Pattern.compile("[a-zA-Z]");
            Matcher matcher = regex.matcher(String.valueOf(character));
            if (matcher.find()) {
                isCharLower = Character.isLowerCase(character);  // to set flag
                character = Character.toUpperCase(character);  // to operate only on upper case chars
                asciFactor = (character + key) - Z_ASCII_INDEX;
                if (asciFactor <= 0) {
                    character = (char) (character + key);
                } else {
                    character = (char) (A_ASCII_INDEX + abs(asciFactor) - 1); // use factor
                }
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