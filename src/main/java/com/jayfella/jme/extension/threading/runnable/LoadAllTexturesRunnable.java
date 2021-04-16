package com.jayfella.jme.extension.threading.runnable;

import com.jayfella.jme.extension.threading.callback.AsyncCallback;
import com.jme3.app.Application;
import com.jme3.texture.Texture;

public class LoadAllTexturesRunnable implements Runnable {

    private final Application app;
    private final String[] names;
    private final AsyncCallback<Texture[]> callback;

    public LoadAllTexturesRunnable(Application app, String[] names, AsyncCallback<Texture[]> callback) {
        this.app = app;
        this.names = names;
        this.callback = callback;
    }

    @Override
    public void run() {

        Texture[] textures = new Texture[names.length];

        for (int i = 0; i < names.length; i++) {
            textures[i] = app.getAssetManager().loadTexture(names[i]);
        }

        app.enqueue(() -> callback.complete(textures));
    }

}
