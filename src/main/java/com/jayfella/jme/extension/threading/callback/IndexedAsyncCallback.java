package com.jayfella.jme.extension.threading.callback;

@FunctionalInterface
public interface IndexedAsyncCallback<T> {

    /**
     * Triggered when a result has completed.
     * @param result the resulting object.
     * @param index  the array index of the resulting object.
     * @param count  the amount of objects that have loaded.
     * @param total  the total amount of objects being loaded.
     */
    void complete(T result, int index, int count, int total);
}
