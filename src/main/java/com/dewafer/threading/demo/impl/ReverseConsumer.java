package com.dewafer.threading.demo.impl;


import com.dewafer.threading.demo.support.Consumer;

import java.util.Random;

public class ReverseConsumer implements Consumer<String, String> {

    private int consumerCount = 0;
    private int consumerAcceptCount = 0;
    private static final Random rand = new Random();

    @Override
    public boolean accept(String s) {
        consumerCount++;
//            if (consumerCount % 1000000 == 0) {
////            System.out.println("Consume[w:" + s + ", reverse:" + result + "]");
//                System.out.println("Consumer alive, consumerCount: " + consumerCount);
//            }
        boolean accept = reverse(s).equals(s);
        if (accept) {
            consumerAcceptCount++;
//                if (consumerAcceptCount % 10000 == 0)
//                    System.out.println("Consumer consumerAcceptCount: " + consumerAcceptCount);
        }
        return accept;
    }

    @Override
    public String consume(String s) {
        // pretend to be hard work
        try {
            int wait = 200 + rand.nextInt(1000);
//                System.out.println("wait:" + wait);
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }

    private String reverse(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            stringBuilder.append(s.charAt(i));
        }
        return stringBuilder.toString();
    }
}