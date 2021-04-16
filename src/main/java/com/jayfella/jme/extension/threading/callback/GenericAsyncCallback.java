package com.jayfella.jme.extension.threading.callback;

public interface GenericAsyncCallback<T> extends AsyncCallback<T> {
    T load();
}
