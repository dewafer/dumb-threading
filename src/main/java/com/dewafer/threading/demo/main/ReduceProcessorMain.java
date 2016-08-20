package com.dewafer.threading.demo.main;

import com.dewafer.threading.demo.RootReduceProcessor;
import com.dewafer.threading.demo.impl.StringProducer;
import com.dewafer.threading.demo.impl.StringReducerImpl;
import com.dewafer.threading.demo.support.RunResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReduceProcessorMain {

    private static final ExecutorService executorServices = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        int runTimes = 3;

        List<RunResult> usedList = new ArrayList<RunResult>();
        System.out.println("run SPP demo");
        for (int i = 0; i < runTimes; i++) {
            usedList.add(runRootProcessor(i));
        }

        System.out.println(usedList);
        System.exit(0);

    }

    private static RunResult runRootProcessor(int runId) {

        System.out.println("Run :" + runId);

        RootReduceProcessor<String, String> demo = new RootReduceProcessor<String, String>(
                new StringProducer(), executorServices, new StringReducerImpl()
        );

        long start = System.currentTimeMillis();
        System.out.println("start:" + start);

        List<String> resultList = demo.execute();

        long end = System.currentTimeMillis();
        System.out.println("end  :" + end);

        long diff = (end - start);
        System.out.println("used(end - start):" + (end - start));
        System.out.println("count: " + resultList.size());

        return new RunResult("SPP", runId, diff, resultList.size());
    }
}
