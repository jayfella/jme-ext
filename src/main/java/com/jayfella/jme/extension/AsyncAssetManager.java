package com.jayfella.jme.extension;

import com.jayfella.jme.extension.threading.callback.AsyncCallback;
import com.jayfella.jme.extension.threading.callback.IndexedAsyncCallback;
import com.jayfella.jme.extension.threading.consumer.LoadAllModelsConsumer;
import com.jayfella.jme.extension.threading.consumer.LoadAllTexturesConsumer;
import com.jayfella.jme.extension.threading.consumer.LoadModelConsumer;
import com.jayfella.jme.extension.threading.consumer.LoadTextureConsumer;
import com.jayfella.jme.extension.threading.runnable.*;
import com.jayfella.jme.extension.threading.supplier.LoadModelSupplier;
import com.jayfella.jme.extension.threading.supplier.LoadModelsSupplier;
import com.jayfella.jme.extension.threading.supplier.LoadTextureSupplier;
import com.jayfella.jme.extension.threading.supplier.LoadTexturesSupplier;
import com.jme3.app.Application;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Loads assets asynchronously.
 *
 * - If no Executor is defined at all, a thread is created using a CompletableFuture.
 * - If an executor is defined in the calling method - that executor is *always* used.
 * - If an executor is set using the #setExecutor(Executor executor) method and no executor is defined in the calling
 *      method, the executor that was set is used.
 * - If an executor is set using the #setExecutor(Executor executor) method and an executor is defined in the calling
 *      method, the executor defined in the method is set.
 *
 */
public class AsyncAssetManager {

    private final Application jmeApp;

    private ExecutorService executorService;
    private boolean manageExecutorShutdown;

    AsyncAssetManager(Application jmeApp) {
        this.jmeApp = jmeApp;
    }

    /**
     * Sets the executor to be used in all asynchronous operations.
     * If an executor is defined in any method, that executor is used instead.
     *
     * @param executorService        the executor to use in all asynchronous operations.
     * @param manageExecutorShutdown determines whether the given ExecutorService will be shutdown by the
     *                               AsyncAssetManager when the application closes.
     */
    public void setExecutorService(ExecutorService executorService, boolean manageExecutorShutdown) {
        this.executorService = executorService;
        this.manageExecutorShutdown = manageExecutorShutdown;
    }

    ExecutorService getExecutorService() {
        return executorService;
    }

    boolean isManageExecutorShutdown() {
        return manageExecutorShutdown;
    }

    /**
     * Loads a single model asynchronously.
     * @param name     the name of the model to load.
     * @param callback a callback that is notified when the operation is complete.
     */
    public void loadModel(String name, AsyncCallback<Spatial> callback) {
        loadModel(name, callback, null);
    }

    /**
     * Loads a single model asynchronously.
     * @param name     the name of the model to load.
     * @param callback a callback that is notified when the operation is complete.
     * @param executor the executor to use to load the model.
     */
    public void loadModel(String name, AsyncCallback<Spatial> callback, Executor executor) {

        if (executor != null) {
            executor.execute(new LoadModelRunnable(jmeApp, name, callback));
        } else if (executorService != null) {
            executorService.execute(new LoadModelRunnable(jmeApp, name, callback));
        } else {
            CompletableFuture.supplyAsync(new LoadModelSupplier(jmeApp.getAssetManager(), name))
                    .thenAccept(new LoadModelConsumer(jmeApp, callback));
        }
    }

    /**
     * Loads multiple models asynchronously, and notifies the callback for each model when loaded.
     * @param callback a callback that is notified when each model is loaded.
     * @param names    the names of the models to load.
     */
    public void loadModels(IndexedAsyncCallback<Spatial> callback, String... names) {
        loadModels(callback, null, names);
    }

    /**
     * Loads multiple models asynchronously, and notifies the callback for each model when loaded.
     * @param callback a callback that is notified when each model is loaded.
     * @param executor the executor to use to loads the models.
     * @param names    the names of the models to load.
     */
    public void loadModels(IndexedAsyncCallback<Spatial> callback, Executor executor, String... names) {

        if (executor != null) {
            executor.execute(new LoadModelsRunnable(jmeApp, names, callback));
        }
        else if (executorService != null) {
            executorService.execute(new LoadModelsRunnable(jmeApp, names, callback));
        }
        else {
            CompletableFuture.runAsync(new LoadModelsRunnable(jmeApp, names, callback));
        }
    }

    /**
     * Loads multiple models asynchronously, and notifies the callback when all models are loaded.
     * @param callback a callback that is notified when all models are loaded.
     * @param names    the names of the models to load.
     */
    public void loadAllModels(AsyncCallback<Spatial[]> callback, String... names) {
        loadAllModels(callback, null, names);
    }

    /**
     * Loads multiple models asynchronously, and notifies the callback when all models are loaded.
     * @param callback a callback that is notified when all models are loaded.
     * @param executor the executor to use to loads the models.
     * @param names    the names of the models to load.
     */
    public void loadAllModels(AsyncCallback<Spatial[]> callback, Executor executor, String... names) {

        if (executor != null) {
            executor.execute(new LoadAllModelsRunnable(jmeApp, names, callback));
        } else if (executorService != null) {
            executorService.execute(new LoadAllModelsRunnable(jmeApp, names, callback));
        } else {
            CompletableFuture.supplyAsync(new LoadModelsSupplier(jmeApp.getAssetManager(), names))
                    .thenAccept(new LoadAllModelsConsumer(jmeApp, callback));
        }
    }


    public void loadTexture(String name, AsyncCallback<Texture> callback) {
        loadTexture(name, callback, null);
    }

    public void loadTexture(String name, AsyncCallback<Texture> callback, Executor executor) {

        if (executor != null) {
            executor.execute(new LoadTextureRunnable(jmeApp, name, callback));
        } else if (executorService != null) {
            executorService.execute(new LoadTextureRunnable(jmeApp, name, callback));
        } else {
            CompletableFuture.supplyAsync(new LoadTextureSupplier(jmeApp.getAssetManager(), name))
                    .thenAccept(new LoadTextureConsumer(jmeApp, callback));
        }

    }

    public void loadTextures(IndexedAsyncCallback<Texture> callback, String... names) {
        loadTextures(callback, null, names);
    }

    public void loadTextures(IndexedAsyncCallback<Texture> callback, Executor executor, String... names) {

        if (executor != null) {
            executor.execute(new LoadTexturesRunnable(jmeApp, names, callback));
        }
        else if (executorService != null) {
            executorService.execute(new LoadTexturesRunnable(jmeApp, names, callback));
        }
        else {
            CompletableFuture.runAsync(new LoadTexturesRunnable(jmeApp, names, callback));
        }

    }

    public void loadAllTextures(AsyncCallback<Texture[]> callback, String... names) {
        loadAllTextures(callback, null, names);
    }

    public void loadAllTextures(AsyncCallback<Texture[]> callback, Executor executor, String... names) {

        if (executor != null) {
            executor.execute((new LoadAllTexturesRunnable(jmeApp, names, callback)));
        }
        else if (executorService != null) {
            executorService.execute((new LoadAllTexturesRunnable(jmeApp, names, callback)));
        }
        else {
            CompletableFuture.supplyAsync(new LoadTexturesSupplier(jmeApp.getAssetManager(), names))
                    .thenAccept(new LoadAllTexturesConsumer(jmeApp, callback));
        }
    }



}
