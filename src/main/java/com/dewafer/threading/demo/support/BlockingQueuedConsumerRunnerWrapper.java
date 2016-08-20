package com.dewafer.threading.demo.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import static com.dewafer.threading.demo.support.RawProductWrapper.END_OF_QUEUE;


public class BlockingQueuedConsumerRunnerWrapper<T, R> implements Callable<List<R>> {

    private Consumer<T, R> consumer;

    private BlockingQueue<RawProductWrapper<T>> rawProductsQueue;


    public BlockingQueuedConsumerRunnerWrapper(BlockingQueue<RawProductWrapper<T>> rawProductsQueue, Consumer<T, R> consumer) {
        this.consumer = consumer;
        this.rawProductsQueue = rawProductsQueue;
    }

    @Override
    public List<R> call() throws Exception {
        List<R> finalProducts = new ArrayList<R>();

        try {
            do {
                RawProductWrapper<T> rawProductWrapper = rawProductsQueue.take();

                if (END_OF_QUEUE.equals(rawProductWrapper)) {
//                    System.out.println("BlockingQueuedConsumerRunnerWrapper exit");
                    break;
                }

                T rawMaterial = rawProductWrapper.getRaw();
                if (consumer.accept(rawMaterial)) {
                    R finalProduct = consumer.consume(rawMaterial);
                    finalProducts.add(finalProduct);
                }
            } while (true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return finalProducts;
    }


}
