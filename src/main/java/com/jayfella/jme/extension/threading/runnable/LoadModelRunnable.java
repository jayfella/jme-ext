package com.jayfella.jme.extension.threading.runnable;

import com.jayfella.jme.extension.threading.callback.AsyncCallback;
import com.jme3.app.Application;
import com.jme3.scene.Spatial;

public final class LoadModelRunnable implements Runnable {

    private final Application app;
    private final String name;
    private final AsyncCallback<Spatial> callback;

    public LoadModelRunnable(Application app, String name, AsyncCallback<Spatial> callback) {
        this.app = app;
        this.name = name;
        this.callback = callback;
    }

    @Override
    public void run() {
        Spatial spatial = app.getAssetManager().loadModel(name);
        app.enqueue(() -> callback.complete(spatial));
    }

}
