package com.jarq.app.factories;

import com.jarq.app.ciphers.Cipher;

public abstract class Factory {

    public abstract <T extends Cipher> T getInstance(Class<T> type);
}
