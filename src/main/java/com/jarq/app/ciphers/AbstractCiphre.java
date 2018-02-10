package com.jarq.app.ciphers;

public abstract class AbstractCiphre implements Ciphre {

    protected boolean isKeyRequider;
    protected String name;
    protected String description;

    public AbstractCiphre() {
        name = "ciphre";
        description = "";
        isKeyRequider = false;
    }

    public abstract String encrypt(String text);
    public abstract String decrypt(String text);

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
