package com.jayfella.jme.extension.threading.runnable;

import com.jayfella.jme.extension.threading.callback.IndexedAsyncCallback;
import com.jme3.app.Application;
import com.jme3.texture.Texture;

public class LoadTexturesRunnable implements Runnable {

    private final Application app;
    private final String[] names;
    private final IndexedAsyncCallback<Texture> callback;

    public LoadTexturesRunnable(Application app, String[] names, IndexedAsyncCallback<Texture> callback) {
        this.app = app;
        this.names = names;
        this.callback = callback;
    }

    @Override
    public void run() {

        for (int i = 0; i < names.length; i++) {

            final int index = i;
            Texture texture = app.getAssetManager().loadTexture(names[index]);
            app.enqueue(() -> callback.complete(texture, index, index + 1, names.length));

        }

    }

}
