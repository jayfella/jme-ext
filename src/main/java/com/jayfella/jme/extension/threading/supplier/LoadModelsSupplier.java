package com.jayfella.jme.extension.threading.supplier;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

import java.util.function.Supplier;

public class LoadModelsSupplier implements Supplier<Spatial[]> {

    private final AssetManager assetManager;
    private final String[] names;

    public LoadModelsSupplier(AssetManager assetManager, String[] names) {
        this.assetManager = assetManager;
        this.names = names;
    }

    @Override
    public Spatial[] get() {

        Spatial[] spatials = new Spatial[names.length];

        for (int i = 0; i < names.length; i++) {
            spatials[i] = assetManager.loadModel(names[i]);
        }

        return spatials;
    }

}
