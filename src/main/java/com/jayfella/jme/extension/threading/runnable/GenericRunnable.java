package com.jayfella.jme.extension.threading.runnable;

import com.jayfella.jme.extension.threading.callback.GenericAsyncCallback;
import com.jme3.app.Application;

public class GenericRunnable<T> implements Runnable {

    private final Application app;
    private final GenericAsyncCallback<T> callback;

    public GenericRunnable(Application app, GenericAsyncCallback<T> callback) {
        this.app = app;
        this.callback = callback;
    }


    @Override
    public void run() {
        T result = callback.load();
        app.enqueue(() -> callback.complete(result));
    }
}
