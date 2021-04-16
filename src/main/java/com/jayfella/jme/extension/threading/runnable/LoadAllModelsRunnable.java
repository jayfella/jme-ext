package com.jayfella.jme.extension.threading.runnable;

import com.jayfella.jme.extension.threading.callback.AsyncCallback;
import com.jme3.app.Application;
import com.jme3.scene.Spatial;

public class LoadAllModelsRunnable implements Runnable {

    private final Application app;
    private final String[] names;
    private final AsyncCallback<Spatial[]> callback;

    public LoadAllModelsRunnable(Application app, String[] names, AsyncCallback<Spatial[]> callback) {
        this.app = app;
        this.names = names;
        this.callback = callback;
    }

    @Override
    public void run() {

        Spatial[] spatials = new Spatial[names.length];

        for (int i = 0; i < names.length; i++) {
            spatials[i] = app.getAssetManager().loadModel(names[i]);
        }

        app.enqueue(() -> callback.complete(spatials));
    }

}