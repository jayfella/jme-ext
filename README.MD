JME Extension
===

The JME Extension library provides additional functionality to a JME application:

Simply extend `JmeSingletonApplication` instead of `SimpleApplication`

- A static singleton class to access throughout your application.
- An asynchronous AssetManager.
- ability to execute code in "the next frame".

### Static Singleton Class

---

To access the JME application anywhere in your code use `JmeSingletonApplication.getInstance()`.

The instance has all the methods usually available, and includes two others:
- enqueueNextFrame(Runnable)
- getAsyncAssetManager()

### Asynchronous AssetManager

---

The asynchronous AssetManager can be accessed anywhere via `JmeSingletonApplication.getInstance().getAsyncAssetManager()`.
The `AsyncAssetManager` will load all assets on a thread other than the main-loop thread. By default, any asynchronous
tasks are executed using the `CompletableFuture` class, though this behavior can be changed.

An `ExecutorService` can be explicitly defined via:
`JmeSingletonApplication.geInstance().getAsyncAssetManager.setExecutorService(executorService, manageExecutorShutdown)`.

If you elect to not have the `AsyncAssetManager` manage the shutdown of the ExecutorService you must do so yourself. If
defined, that `ExecutorService` will be used instead of the `CompletableFuture` class **unless** an `ExecutorService` is
defined in the method call (see below).

An `ExecutorService` can be defined in each call via a method overload. For example:

`JmeSingletonApplication.getInstance().getAsyncAssetManager().loadModel(String name, Executor executor)`

If an `ExecutorService` is defined in the method call, that `ExecutorService` will **always** be used.

In summary: If no `ExecutorService` is defined at all, the action will be run using the `CompletableFuture` class. If
an `ExecutorService` is set, it will use that `ExecutorService` instead of a `CompletableFuture`. If an `ExecutorService`
is defined in the method, that `ExecutorService` will **always** be used, regardless of whether or not one has been set.

This behavior is defined in code as:

```java
public void loadTexture(String name, ExecutorService executor, AsyncCallback<Texture>) {
    
    if (executor != null) {
        // use the given executor...
    }
    else if (executorService != null) {
        // use the ExecutorService defined in .setExecutorService 
    }
    else {
        // use the CompletableFuture class.
    }
    
}
```

#### Loading Variations

There are 3 ways to load assets asynchronously. Most notably, you can load items as a singular or in multiples. In the
case of multiples you can opt to get the result as they are loaded or all at once.

```
- Load a single asset.
    - loadModel(String name, AsyncCallback<Spatial> callback);
    - loadTexture(String name, AsyncCallback<Texture> callback);

- Load multiple assets and return them as they are loaded.
    - loadAllModels(IndexedAsyncCallback<Spatial> callback, String... names)
    - loadAllTextures(IndexedAsyncCallback<Texture> callback, String... names)
    
- Load multiple assets and return the complete set.
    - loadAllModels(AsyncCallback<Spatial[]> callback, String... names)
    - loadAllTextures(AsyncCallback<Texture[]> callback, String... names)
```

### Next-Frame Execution

---

Executes given runnables in "the next frame".

This behavior is leveraged by utilizing the `simpleUpdate(float tpf)` method. This method is called **first** - before
any AppState, and therefore any runnable that is added to the "next frame" queue will be added after this list is emptied,
and so guaranteed to run in the next frame.

Below is an over-simplified example of how this works.

```java
- Game Loop
    - simpleUpdate
        - execute any Runnables in the "next frame" queue.
    - AppStates...
        - AppState calls JmeSingletonApplication().getInstance().enqueueNextFrame(runnable).

... end of loop. start again ...
```

One of the great things about this ability is to be able to add a new `AppState` to the `AppStateManager` and execute
code in the next loop after the `AppState` has been initialized:

```java
getStateManager.attach(new MyAppState());
JmeSingletonApplication().getInstance().enqueueNextFrame(() -> getStateManager.getState(MyAppState.class).doSomethingAfterInitialization());
```