package com.dewafer.threading.demo;


import com.dewafer.threading.demo.support.BlockingQueuedConsumerRunnerWrapper;
import com.dewafer.threading.demo.support.Consumer;
import com.dewafer.threading.demo.support.Producer;
import com.dewafer.threading.demo.support.RawProductWrapper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static com.dewafer.threading.demo.support.RawProductWrapper.END_OF_QUEUE;


public class SingleProducerSingleConsumerDemo<T, R> {

    private ExecutorService executor;
    private Producer<T> producer;
    private Consumer<T, R> consumer;

    private BlockingQueue<RawProductWrapper<T>> rawProductsQueue = new LinkedBlockingQueue<RawProductWrapper<T>>();


    public SingleProducerSingleConsumerDemo(ExecutorService executor, Producer<T> producer, Consumer<T, R> consumer) {
        this.executor = executor;
        this.producer = producer;
        this.consumer = consumer;
    }

    public List<R> execute() {

        Future<List<R>> future = executor.submit(new BlockingQueuedConsumerRunnerWrapper<T, R>(
                rawProductsQueue, consumer
        ));

        try {
            while (producer.hasNext()) {
                T rawProduct = producer.produce();
                rawProductsQueue.put(RawProductWrapper.of(rawProduct));
            }
            rawProductsQueue.put((RawProductWrapper<T>) END_OF_QUEUE);
//            System.out.println("producer finished");

//            System.out.println("wait on converter");

            return future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


}
