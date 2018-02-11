package com.jarq.app.ciphers;

public interface Ciphre {

    String execute(String text, String mode);
    boolean isKeyRequired();

}
