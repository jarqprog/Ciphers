package com.jarq.app.ciphers;

public interface Ciphre {

    String encrypt(String text);
    String decrypt(String text);
    boolean isKeyRequired();

}
