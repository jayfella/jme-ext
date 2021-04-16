package com.jayfella.jme.extension.threading.consumer;

import com.jayfella.jme.extension.threading.callback.GenericAsyncCallback;
import com.jme3.app.Application;

import java.util.function.Consumer;

public class GenericConsumer<T> implements Consumer<T> {
    
    private final Application app;
    private final GenericAsyncCallback<T> callback;

    public GenericConsumer(Application app, GenericAsyncCallback<T> callback) {
        this.app = app;
        this.callback = callback;
    }

    @Override
    public void accept(T t) {
        app.enqueue(() -> callback.complete(t));
    }
    
}
