package com.dewafer.threading.demo.impl;


import com.dewafer.threading.demo.support.AbstractListSplittableTask;
import com.dewafer.threading.demo.support.Reducer;
import com.dewafer.threading.demo.support.TaskResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ReduceTaskImpl<O, R> extends AbstractListSplittableTask<O, R> {

    private ExecutorService executorService;
    private Reducer<O, R> reducer;

    public ReduceTaskImpl(List<O> originList, ExecutorService executorService, Reducer<O, R> reducer) {
        super(originList);
        this.executorService = executorService;
        this.reducer = reducer;
    }

    @Override
    public Future<TaskResult<R>> submitSubTask(List<O> subList) {
        return executorService.submit(new ReduceTaskImpl<O, R>(subList, executorService, reducer));
    }

    @Override
    public List<R> processOwn(List<O> ownList) {
        List<R> reducedList = new ArrayList<R>();

        for (O origin : ownList) {
            R reduced = reducer.reduce(origin);
            if (reduced != null) {
                reducedList.add(reduced);
            }
        }

        return reducedList;
    }
}
