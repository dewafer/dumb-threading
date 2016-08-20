package com.dewafer.threading.demo.impl;


import com.dewafer.threading.demo.support.Reducer;

import java.util.Random;

public class StringReducerImpl implements Reducer<String, String> {

    private static final Random rand = new Random();

    @Override
    public String reduce(String origin) {
        if (reverse(origin).equals(origin)) {
            // pretend to be hard work
            try {
                int wait = 200 + rand.nextInt(1000);
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return origin;
        } else {
            return null;
        }
    }

    private String reverse(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            stringBuilder.append(s.charAt(i));
        }
        return stringBuilder.toString();
    }

}
