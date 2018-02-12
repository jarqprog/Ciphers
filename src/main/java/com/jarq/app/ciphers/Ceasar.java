package com.jarq.app.ciphers;

import com.jarq.app.exceptions.InvalidKey;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class Ceasar extends SimpleSubstitution {

    public Ceasar() {
        name = "Ceasar";
        description = "is one of the simplest and most widely known encryption techniques.\n" +
                "        It is a type of substitution cipher in which each letter in the plaintext is replaced\n" +
                "        by a letter some fixed number of positions down the alphabet.";
        isKeyRequired = true;
    }

    @Override
    public void changeKey(String newKey) throws InvalidKey, NumberFormatException {
        Pattern regex = Pattern.compile("[0-9]+");
        Matcher matcher = regex.matcher(newKey);
        if (matcher.find()){
            int key = abs(Integer.parseInt(newKey));
            if(key > 24) {
                throw new InvalidKey();
            }
            this.setKey(key);
        } else {
            throw new InvalidKey();
        }
    }
}
