package com.jayfella.jme.extension;

import com.jayfella.jme.extension.threading.callback.GenericAsyncCallback;
import com.jayfella.jme.extension.threading.consumer.GenericConsumer;
import com.jayfella.jme.extension.threading.runnable.GenericRunnable;
import com.jayfella.jme.extension.threading.supplier.GenericSupplier;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public abstract class SingletonJmeApplication extends SimpleApplication {
    
    private static SingletonJmeApplication INSTANCE;

    private final Deque<Runnable> runnableDeque = new ArrayDeque<>();
    private final AsyncAssetManager asyncAssetManager;

    private ExecutorService executorService;
    private boolean manageExecutorShutdown;

    public SingletonJmeApplication() {
        this((AppState[]) null);
    }

    public SingletonJmeApplication(AppState... initialStates) {

        // slightly modify the way JME initializes default states because we want INSTANCE
        // to have a value before we start any states. This circumvents any issue of them
        // calling JmeApplication.getInstance() returning null if the add them in the constructor.
        // This should not interrupt the usual expected flow (i.e. expected "initialStates" states are laded).

        super(new AppState[0]);

        INSTANCE = this;
        asyncAssetManager = new AsyncAssetManager(this);

        // finally
        initializeStates(initialStates);
    }

    private void initializeStates(AppState... initialStates) {

        if (initialStates == null) {
            getStateManager().attachAll(new StatsAppState(),
                    new FlyCamAppState(),
                    new AudioListenerState(),
                    new DebugKeysAppState(),
                    new ConstantVerifierState()
            );
        }
        else if (initialStates.length > 0) {
            getStateManager().attachAll(initialStates);
        }
    }

    public static SingletonJmeApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void simpleInitApp() {

        initializeApp();
    }

    public abstract void initializeApp();

    @Override
    public void simpleUpdate(float tpf) {

        // since simpleUpdate is called first, we can deduce that any runnable in the list
        // is going to be executed in the next frame - i.e. the frame after they were enqueued.

        while (!runnableDeque.isEmpty()) {
            enqueue(runnableDeque.pop());
        }

        updateApp(tpf);
    }

    public abstract void updateApp(float tpf);

    /**
     * executes the given Runnable in the next frame.
     * @param runnable the Runnable to execute.
     */
    public boolean enqueueNextFrame(Runnable runnable) {
        synchronized (runnableDeque) {
            return runnableDeque.add(runnable);
        }
    }

    public AsyncAssetManager getAsyncAssetManager() {
        return asyncAssetManager;
    }

    public void setExecutorService(ExecutorService executorService, boolean manageExecutorShutdown) {
        this.executorService = executorService;
        this.manageExecutorShutdown = manageExecutorShutdown;
    }

    public <T> void executeAsync(GenericAsyncCallback<T> callback) {
        executeAsync(callback, null);
    }

    public <T> void executeAsync(GenericAsyncCallback<T> callback, Executor executor) {

        if (executor != null) {
            executor.execute(new GenericRunnable<>(this, callback));
        }
        else if (this.executorService != null) {
            this.executorService.execute(new GenericRunnable<>(this, callback));
        }
        else {
            CompletableFuture.supplyAsync(new GenericSupplier<>(callback))
                    .thenAccept(new GenericConsumer<>(this, callback));
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        // if the user has elected to let SingletonJmeApplication control its ExecutorService lifecycle, shut it down.
        if (executorService != null && manageExecutorShutdown) {
            executorService.shutdownNow();
        }

        // if the user has elected to let AsyncAssetManager control its ExecutorService lifecycle, shut it down.
        if (asyncAssetManager.getExecutorService() != null && asyncAssetManager.isManageExecutorShutdown()) {
            asyncAssetManager.getExecutorService().shutdownNow();
        }

    }

}
