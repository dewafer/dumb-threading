package com.dewafer.threading.demo.support;

public class Tuple<Left, Right> {
    Left left;
    Right right;

    public Tuple(Left left, Right right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Tuple<L, R> of(L left, R right) {
        return new Tuple<L, R>(left, right);
    }
}
