package com.dewafer.threading.demo.support;

import java.util.concurrent.Callable;

public class NoneBlockingConsumerRunnerWrapper<T, R> implements Callable<R> {

    private Consumer<T, R> consumer;

    private T rawProduct;

    public NoneBlockingConsumerRunnerWrapper(Consumer<T, R> consumer, T rawProduct) {
        this.consumer = consumer;
        this.rawProduct = rawProduct;
    }

    @Override
    public R call() throws Exception {
        if (consumer.accept(rawProduct)) {
            return consumer.consume(rawProduct);
        } else {
            return null;
        }
    }
}
