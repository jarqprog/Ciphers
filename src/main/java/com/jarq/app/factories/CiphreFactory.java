package com.jarq.app.factories;

import com.jarq.app.ciphers.Ciphre;
import com.jarq.app.ciphers.Playfair;
import com.jarq.app.ciphers.Rot13;

public class CiphreFactory extends Factory {


    @SuppressWarnings("unchecked")
    public <T extends Ciphre> T getInstance(Class<T> type) {

        T cipher = null;

        if (type.getClass().getName().equals(Rot13.class.getClass().getName())) {
            cipher = (T) new Rot13();

        }  else if (type.getClass().getName().equals(Playfair.class.getClass().getName())) {
            cipher = (T) new Playfair();
        }
        return cipher;
    }
}
