package com.jarq.app.ciphers;

public class Rot13 extends SimpleSubstitution {

    private int key;

    public Rot13() {
        description = " - is a simple letter substitution cipher that replaces a letter with the 13th letter\n"+
                "after it, in the alphabet. ROT13 is a special case of the Caesar cipher,\n"+
                "developed in ancient Rome.";
        isKeyRequider = false;
        this.setKey(13);
    }
}
