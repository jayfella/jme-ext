package com.jayfella.jme.extension.threading.callback;

@FunctionalInterface
public interface AsyncCallback<T> {
    void complete(T result);
}