package com.jayfella.jme.extension.threading.consumer;

import com.jayfella.jme.extension.threading.callback.AsyncCallback;
import com.jme3.app.Application;
import com.jme3.scene.Spatial;

import java.util.function.Consumer;

public class LoadModelConsumer implements Consumer<Spatial> {

    private final Application app;
    private final AsyncCallback<Spatial> callback;

    public LoadModelConsumer(Application app, AsyncCallback<Spatial> callback) {
        this.app = app;
        this.callback = callback;
    }

    @Override
    public void accept(Spatial spatial) {
        app.enqueue(() -> callback.complete(spatial));
    }

}
