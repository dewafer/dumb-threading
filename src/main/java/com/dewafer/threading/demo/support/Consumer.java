package com.dewafer.threading.demo.support;

public interface Consumer<T, R> {
    public boolean accept(T t);

    public R consume(T t);
}
