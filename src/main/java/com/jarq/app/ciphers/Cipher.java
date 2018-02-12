package com.jarq.app.ciphers;

import com.jarq.app.exceptions.InvalidKey;

public interface Cipher {

    String execute(String text, String mode);
    boolean isKeyRequired();
    String getKeyInfo();
    void changeKey(String key) throws InvalidKey;
}
