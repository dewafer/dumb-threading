package com.dewafer.threading.demo.support;

public interface ConsumerFactory<T, R> {

    public Consumer<T, R> create();
}
