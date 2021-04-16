package com.jayfella.jme.extension.threading.runnable;

import com.jayfella.jme.extension.threading.callback.AsyncCallback;
import com.jme3.app.Application;
import com.jme3.texture.Texture;

public class LoadTextureRunnable implements Runnable {

    private final Application app;
    private final String name;
    private final AsyncCallback<Texture> callback;

    public LoadTextureRunnable(Application app, String name, AsyncCallback<Texture> callback) {
        this.app = app;
        this.name = name;
        this.callback = callback;
    }

    @Override
    public void run() {
        Texture texture = app.getAssetManager().loadTexture(name);
        app.enqueue(() -> callback.complete(texture));
    }

}
