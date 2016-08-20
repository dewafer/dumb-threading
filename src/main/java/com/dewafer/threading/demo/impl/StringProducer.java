package com.dewafer.threading.demo.impl;


import com.dewafer.threading.demo.support.Producer;

import java.util.Random;

public class StringProducer implements Producer<String> {

    private static final int MAX = 1000;
    private int produceCount = 0;
    private Random rand = new Random();
    private static final String characters = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public String produce() {
        StringBuilder stringBuilder = new StringBuilder();
        int wordsCount = 3 + rand.nextInt(5);
        for (int i = 0; i < wordsCount; i++) {
            stringBuilder.append(characters.charAt(rand.nextInt(characters.length())));
        }

        produceCount++;
//            if (produceCount % 1000000 == 0) {
//                // System.out.println("Produce[i:" + produceCount + ", w:" + stringBuilder.toString() + "]");
//                System.out.println("Produce alive, produceCount: " + produceCount);
//            }

        return stringBuilder.toString();
    }

    @Override
    public boolean hasNext() {
        return produceCount < MAX;
    }
}