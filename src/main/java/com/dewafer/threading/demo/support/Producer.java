package com.dewafer.threading.demo.support;


public interface Producer<T> {
    public T produce();

    public boolean hasNext();
}
