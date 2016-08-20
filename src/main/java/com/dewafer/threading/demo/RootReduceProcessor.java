package com.dewafer.threading.demo;

import com.dewafer.threading.demo.impl.ReduceTaskImpl;
import com.dewafer.threading.demo.support.Producer;
import com.dewafer.threading.demo.support.Reducer;
import com.dewafer.threading.demo.support.TaskResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class RootReduceProcessor<O, R> {

    private Producer<O> producer;
    private ExecutorService executorService;
    private Reducer<O, R> reducer;

    public RootReduceProcessor(Producer<O> producer, ExecutorService executorService, Reducer<O, R> reducer) {
        this.producer = producer;
        this.executorService = executorService;
        this.reducer = reducer;
    }

    public List<R> execute() {
        List<O> rootList = new ArrayList<O>();
        while (producer.hasNext()) {
            rootList.add(producer.produce());
        }
        System.out.println("rootList created, size:" + rootList.size());

        ReduceTaskImpl<O, R> rootReduceTask = new ReduceTaskImpl<O, R>(rootList, executorService, reducer);
        Future<TaskResult<R>> future = executorService.submit(rootReduceTask);

        System.out.println("root reduce task submitted, wait for sub tasks to complete");

        // wait for root task to complete
        try {
            return future.get().merge();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

}
