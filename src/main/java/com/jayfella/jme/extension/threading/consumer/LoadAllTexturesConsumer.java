package com.jayfella.jme.extension.threading.consumer;

import com.jayfella.jme.extension.threading.callback.AsyncCallback;
import com.jme3.app.Application;
import com.jme3.texture.Texture;

import java.util.function.Consumer;

public class LoadAllTexturesConsumer implements Consumer<Texture[]> {

    private final Application app;
    private final AsyncCallback<Texture[]> callback;

    public LoadAllTexturesConsumer(Application app, AsyncCallback<Texture[]> callback) {
        this.app = app;
        this.callback = callback;
    }

    @Override
    public void accept(Texture[] textures) {
        app.enqueue(() -> callback.complete(textures));
    }

}
