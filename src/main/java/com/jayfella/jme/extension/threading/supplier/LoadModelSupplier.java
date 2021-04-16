package com.jayfella.jme.extension.threading.supplier;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

import java.util.function.Supplier;

public class LoadModelSupplier implements Supplier<Spatial> {

    private final AssetManager assetManager;
    private final String name;

    public LoadModelSupplier(AssetManager assetManager, String name) {
        this.assetManager = assetManager;
        this.name = name;
    }

    @Override
    public Spatial get() {
        return assetManager.loadModel(name);
    }

}
