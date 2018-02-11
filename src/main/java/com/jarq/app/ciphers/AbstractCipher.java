package com.jarq.app.ciphers;

import com.jarq.app.enums.Procedure;

public abstract class AbstractCipher implements Cipher {

    protected boolean isKeyRequider;
    protected String name;
    protected String description;

    public AbstractCipher() {
        name = "cipher";
        description = "";
        isKeyRequider = false;
    }

    public String execute(String text, String mode) {
        if (mode.equals(Procedure.ENCRYPTION.getMode())) {
            return encrypt(text);
        }
        return decrypt(text);
    }

    protected abstract String encrypt(String text);

    protected abstract String decrypt(String text);

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String toString() {
        return String.format("cipher: %s\n%s\n is key required: %s",
                name, description, String.valueOf(isKeyRequider));
    }

    public boolean isKeyRequired() {
        return isKeyRequider;
    }

}