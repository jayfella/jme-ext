package com.jayfella.jme.extension.threading.runnable;

import com.jayfella.jme.extension.threading.callback.IndexedAsyncCallback;
import com.jme3.app.Application;
import com.jme3.scene.Spatial;

public class LoadModelsRunnable implements Runnable {

    private final Application app;
    private final String[] names;
    private final IndexedAsyncCallback<Spatial> callback;

    public LoadModelsRunnable(Application app, String[] names, IndexedAsyncCallback<Spatial> callback) {
        this.app = app;
        this.names = names;
        this.callback = callback;
    }

    @Override
    public void run() {

        for (int i = 0; i < names.length; i++) {

            final int index = i;
            Spatial spatial = app.getAssetManager().loadModel(names[index]);
            app.enqueue(() -> callback.complete(spatial, index, index + 1, names.length));

        }

    }

}
