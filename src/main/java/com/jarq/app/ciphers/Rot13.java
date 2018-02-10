package com.jarq.app.ciphers;

import com.jarq.app.enums.Procedure;

public class Rot13 extends AbstractCiphre {

    private String text;
    private String mode;

    public Rot13() {
        text = "";
        description = " - is a simple letter substitution cipher that replaces a letter with the 13th letter\n"+
                "after it, in the alphabet. ROT13 is a special case of the Caesar cipher,\n"+
                "developed in ancient Rome.";
        isKeyRequider = false;
        mode = Procedure.DECRYPTION.getMode();
    }

    public String encrypt(String text) {


        return null;
    }

    public String decrypt(String text) {


        return null;
    }
}
