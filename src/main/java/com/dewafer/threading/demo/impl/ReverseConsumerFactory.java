package com.dewafer.threading.demo.impl;


import com.dewafer.threading.demo.support.Consumer;
import com.dewafer.threading.demo.support.ConsumerFactory;

public class ReverseConsumerFactory implements ConsumerFactory<String, String> {

    @Override
    public Consumer<String, String> create() {
        return new ReverseConsumer();
    }
}
