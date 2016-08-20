package com.dewafer.threading.demo;

import com.dewafer.threading.demo.support.Consumer;
import com.dewafer.threading.demo.support.ConsumerFactory;
import com.dewafer.threading.demo.support.NoneBlockingConsumerRunnerWrapper;
import com.dewafer.threading.demo.support.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SingleProducerMultiConsumerDemo<T, R> {

    private ExecutorService executor;
    private Producer<T> producer;
    private ConsumerFactory<T, R> consumerFactory;

    public SingleProducerMultiConsumerDemo(ExecutorService executor, Producer<T> producer, ConsumerFactory<T, R> consumerFactory) {
        this.executor = executor;
        this.producer = producer;
        this.consumerFactory = consumerFactory;
    }

    public List<R> execute() {

        List<Future<R>> futures = new ArrayList<Future<R>>();

        while (producer.hasNext()) {
            T rawProduct = producer.produce();
            Consumer<T, R> consumer = consumerFactory.create();
            futures.add(
                    executor.submit(new NoneBlockingConsumerRunnerWrapper<T, R>(consumer, rawProduct))
            );
        }

        List<R> result = new ArrayList<R>();
        for (Future<R> future : futures) {
            try {
                R finalProduct = future.get();
                if (finalProduct != null) {
                    result.add(finalProduct);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
