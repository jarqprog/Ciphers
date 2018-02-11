package com.jarq.app.ciphers;

import com.jarq.app.enums.Procedure;

public abstract class AbstractCiphre implements Ciphre {

    protected boolean isKeyRequider;
    protected String name;
    protected String description;

    public AbstractCiphre() {
        name = "ciphre";
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
        return String.format("ciphre: %s\n%s\n is key required: %s",
                name, description, String.valueOf(isKeyRequider));
    }

    public boolean isKeyRequired() {
        return isKeyRequider;
    }

}
