package com.jarq.app.ciphers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SimpleSubstitution extends AbstractCipher {

    protected int key;
    protected final Pattern regex = Pattern.compile("[a-zA-Z]");
    protected final int ASCII_FACTOR = 26;

    public SimpleSubstitution() {
        isKeyRequired = true;
        description = "helper cipher";
    }

    protected void setKey(int newKey) {
        key = newKey;
    }

    protected String encrypt(String text) {
        boolean isCharLower;
        int length = text.length();
        char ch;
        Matcher matcher;
        StringBuilder encrypted = new StringBuilder();
        for(int i=0; i<length; i++) {
            ch = text.charAt(i);
            matcher = regex.matcher(String.valueOf(ch));
            if (matcher.find()) {
                isCharLower = Character.isLowerCase(ch);  // to set flag
                ch = Character.toUpperCase(ch);  // to operate only on upper case chars
                ch = (char) (ch + (key % ASCII_FACTOR));
                if(ch > 'Z') {
                    ch = (char) (ch - ASCII_FACTOR);
                }
                if(isCharLower) {
                    ch = Character.toLowerCase(ch);
                }
            }
            encrypted.append(ch);
        }
        return encrypted.toString();
    }

    protected String decrypt(String text) {
        boolean isCharLower;
        int length = text.length();
        char ch;
        Matcher matcher;
        StringBuilder encrypted = new StringBuilder();
        for(int i=0; i<length; i++) {
            ch = text.charAt(i);
            matcher = regex.matcher(String.valueOf(ch));
            if (matcher.find()) {
                isCharLower = Character.isLowerCase(ch);  // to set flag
                ch = Character.toUpperCase(ch);  // to operate only on upper case chars
                ch = (char) (ch - (key % ASCII_FACTOR));
                if(ch < 'A') {
                    ch = (char) (ch + ASCII_FACTOR);
                }
                if(isCharLower) {
                    ch = Character.toLowerCase(ch);
                }
            }
            encrypted.append(ch);
        }
        return encrypted.toString();
    }
}