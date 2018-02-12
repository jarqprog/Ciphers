package com.jarq.app.ciphers;

import com.jarq.app.enums.Procedure;
import com.jarq.app.exceptions.InvalidKey;

public abstract class AbstractCipher implements Cipher {

    protected boolean isKeyRequired;
    protected String name;
    protected String description;

    public AbstractCipher() {
        name = "cipher";
        description = "";
        isKeyRequired = false;
    }

    public String execute(String text, String mode) {
        if (mode.equals(Procedure.ENCRYPTION.getMode())) {
            return "enc(" + encrypt(text) + ")";
        }
        return "dec(" + decrypt(text) + ")";
    }

    public String getKeyInfo() {
        return "not required";
    }

    public void changeKey(String newKey) throws InvalidKey {
    }

    public void setIsKeyRequired(boolean b) {
        this.isKeyRequired = b;
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
        return String.format("\n        %s - %s\n\n",
                name, description);
    }

    public boolean isKeyRequired() {
        return isKeyRequired;
    }

}
