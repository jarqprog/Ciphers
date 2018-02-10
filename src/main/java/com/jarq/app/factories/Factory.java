package com.jarq.app.factories;

import com.jarq.app.ciphers.Ciphre;

public abstract class Factory {

    public abstract <T extends Ciphre> T getInstance(Class<T> type);
}
