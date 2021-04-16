package com.jayfella.jme.extension.threading.supplier;

import com.jayfella.jme.extension.threading.callback.GenericAsyncCallback;

import java.util.function.Supplier;

public class GenericSupplier<T> implements Supplier<T> {

    private final GenericAsyncCallback<T> callback;

    public GenericSupplier(GenericAsyncCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public T get() {
        return callback.load();
    }

}
