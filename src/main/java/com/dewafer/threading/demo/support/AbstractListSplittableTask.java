package com.dewafer.threading.demo.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.dewafer.threading.demo.support.Log.println;


public abstract class AbstractListSplittableTask<O, R> implements Callable<TaskResult<R>> {

    private List<O> originList;
    private static final int THRESHOLD = 100;

    public AbstractListSplittableTask(List<O> originList) {
        this.originList = originList;
    }

    public TaskResult<R> call() throws Exception {

        List<O> ownList = originList;
        Future<TaskResult<R>> subTaskFuture = null;

        Tuple<List<O>, List<O>> splitted = trySplit(originList);
        if (splitted != null) {
            ownList = splitted.left;
            println(Thread.currentThread().getName() + " origin list(size:" + originList.size() + ") splitted(left:" + splitted.left.size() + ", right:" + splitted.right.size() + "), submitting sub task");
            subTaskFuture = submitSubTask(splitted.right);
        } else {
            println(Thread.currentThread().getName() + " origin list can't be splitted, will only process all the list");
        }

        List<R> resultList = createResultList();

        println(Thread.currentThread().getName() + " start to process own list, size:" + ownList.size());
        // process own sublist
        resultList.addAll(processOwn(ownList));

        println(Thread.currentThread().getName() + " own list processed, return result");
        return TaskResult.of(resultList, subTaskFuture);
    }

    protected List<R> createResultList() {
        return new ArrayList<R>();
    }

    public abstract Future<TaskResult<R>> submitSubTask(List<O> subList);

    protected Tuple<List<O>, List<O>> trySplit(List<O> origin) {
        // TODO update to be greater performance
        if (origin.size() <= THRESHOLD) {
            return null;
        } else {
            int splitSize = THRESHOLD;

            return Tuple.of(
                    origin.subList(0, splitSize), // left
                    origin.subList(splitSize, origin.size() - 1) // right
            );
        }
    }

    public abstract List<R> processOwn(List<O> ownList);

}
