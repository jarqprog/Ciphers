package com.jarq.app.factories;

import com.jarq.app.ciphers.Ceasar;
import com.jarq.app.ciphers.Cipher;
import com.jarq.app.ciphers.Playfair;
import com.jarq.app.ciphers.Rot13;

public class CiphreFactory extends Factory {


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
        }
        return cipher;
    }
}
