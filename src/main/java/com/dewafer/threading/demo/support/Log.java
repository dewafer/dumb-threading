package com.dewafer.threading.demo.support;

public class Log {

    private static boolean debugOutput = true;

    public static void println(Object object) {
        if (debugOutput) {
            System.out.println(object);
        }
    }
}
