package com.jayfella.jme.extension.threading.supplier;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;

import java.util.function.Supplier;

public class LoadTextureSupplier implements Supplier<Texture> {

    private final AssetManager assetManager;
    private final String name;

    public LoadTextureSupplier(AssetManager assetManager, String name) {
        this.assetManager = assetManager;
        this.name = name;
    }

    @Override
    public Texture get() {
        return assetManager.loadTexture(name);
    }

}
