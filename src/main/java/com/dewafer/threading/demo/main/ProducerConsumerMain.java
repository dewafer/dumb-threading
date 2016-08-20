package com.dewafer.threading.demo.main;


import com.dewafer.threading.demo.SingleProducerMultiConsumerDemo;
import com.dewafer.threading.demo.SingleProducerSingleConsumerDemo;
import com.dewafer.threading.demo.impl.ReverseConsumer;
import com.dewafer.threading.demo.impl.ReverseConsumerFactory;
import com.dewafer.threading.demo.impl.StringProducer;
import com.dewafer.threading.demo.support.RunResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerMain {

    private static final ExecutorService executorServices = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {

        int runTimes = 3;

        List<RunResult> usedList = new ArrayList<RunResult>();
        System.out.println("run SPSC demo");
        for (int i = 0; i < runTimes; i++) {
            usedList.add(runSPSCDemo(i));
        }

        System.out.println("run SPMC demo");
        for (int i = 0; i < runTimes; i++) {
            usedList.add(runSPMCDemo(i));
        }

        System.out.println(usedList);
        System.exit(0);

    }

    private static RunResult runSPSCDemo(int runId) {

        System.out.println("Run :" + runId);

        SingleProducerSingleConsumerDemo<String, String> demo = new SingleProducerSingleConsumerDemo<String, String>(
                executorServices,
                new StringProducer(), new ReverseConsumer()
        );

        long start = System.currentTimeMillis();
        System.out.println("start:" + start);

        List<String> resultList = demo.execute();

        long end = System.currentTimeMillis();
        System.out.println("end  :" + end);

        long diff = (end - start);
        System.out.println("used(end - start):" + (end - start));
        System.out.println("count: " + resultList.size());

        return new RunResult("SPSC", runId, diff, resultList.size());
    }

    private static RunResult runSPMCDemo(int runId) {

        System.out.println("Run :" + runId);

        SingleProducerMultiConsumerDemo<String, String> demo = new SingleProducerMultiConsumerDemo<String, String>(
                executorServices,
                new StringProducer(), new ReverseConsumerFactory()
        );

        long start = System.currentTimeMillis();
        System.out.println("start:" + start);

        List<String> resultList = demo.execute();

        long end = System.currentTimeMillis();
        System.out.println("end  :" + end);

        long diff = (end - start);
        System.out.println("used(end - start):" + (end - start));
        System.out.println("count: " + resultList.size());

        return new RunResult("SPMC", runId, diff, resultList.size());
    }

}
