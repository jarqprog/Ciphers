package com.jarq.app.factories;

import com.jarq.app.ciphers.*;

public class CipherFactory extends Factory {


    @SuppressWarnings("unchecked")
    public <T extends Cipher> T getInstance(Class<T> type) {

        String className = type.getName();

        T cipher = null;

        if (className.equals(Rot13.class.getName())) {
            cipher = (T) new Rot13();
        }  else if (className.equals(Playfair.class.getName())) {
            cipher = (T) new Playfair();
        } else if (className.equals(Ceasar.class.getName())) {
            cipher = (T) new Ceasar();
        } else if (className.equals(Atbash.class.getName())) {
            cipher = (T) new Atbash();
        }
        return cipher;
    }
}
