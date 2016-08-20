package com.dewafer.threading.demo.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TaskResult<R> {

    private List<R> ownResultList;
    private Future<TaskResult<R>> subTaskResultFuture;

    public TaskResult(List<R> ownResultList, Future<TaskResult<R>> subTaskResultFuture) {
        this.ownResultList = ownResultList;
        this.subTaskResultFuture = subTaskResultFuture;
    }

    public List<R> getOwnResultList() {
        return ownResultList;
    }

    public Future<TaskResult<R>> getSubTaskResultFuture() {
        return subTaskResultFuture;
    }

    public List<R> merge() {
        if (subTaskResultFuture == null) {
            return getOwnResultList();
        } else {

            List<R> mergedResult = new ArrayList<R>(getOwnResultList());

            try {
                mergedResult.addAll(subTaskResultFuture.get().merge());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            return mergedResult;
        }
    }

    public static <R> TaskResult<R> of(List<R> ownResultList, Future<TaskResult<R>> subTaskResultFuture) {
        return new TaskResult<R>(ownResultList, subTaskResultFuture);
    }
}
