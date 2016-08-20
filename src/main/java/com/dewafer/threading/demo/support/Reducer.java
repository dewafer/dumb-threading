package com.dewafer.threading.demo.support;

public interface Reducer<O, R> {

    public R reduce(O origin);

}
