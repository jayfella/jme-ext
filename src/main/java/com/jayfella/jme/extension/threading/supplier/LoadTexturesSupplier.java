package com.jayfella.jme.extension.threading.supplier;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;

import java.util.function.Supplier;

public class LoadTexturesSupplier implements Supplier<Texture[]> {

    private final AssetManager assetManager;
    private final String[] names;

    public LoadTexturesSupplier(AssetManager assetManager, String[] names) {
        this.assetManager = assetManager;
        this.names = names;

    }

    @Override
    public Texture[] get() {

        Texture[] textures = new Texture[names.length];

        for (int i = 0; i < names.length; i++) {
            textures[i] = assetManager.loadTexture(names[i]);
        }

        return textures;
    }

}
