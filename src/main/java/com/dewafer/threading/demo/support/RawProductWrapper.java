package com.dewafer.threading.demo.support;

public class RawProductWrapper<T> {

    public static final RawProductWrapper END_OF_QUEUE = RawProductWrapper.of(null);

    private T raw;

    RawProductWrapper(T raw) {
        this.raw = raw;
    }

    public static <T> RawProductWrapper of(T raw) {
        return new RawProductWrapper<T>(raw);
    }

    public T getRaw() {
        return raw;
    }
}
