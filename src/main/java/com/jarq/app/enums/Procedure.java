package com.jarq.app.enums;

public enum Procedure {

    ENCRYPTION("encryption"),
    DECRYPTION("decryption");

    private String mode;

    private Procedure(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}