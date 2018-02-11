package com.jarq.app.ciphers;

public interface Cipher {

    String execute(String text, String mode);
    boolean isKeyRequired();

}
