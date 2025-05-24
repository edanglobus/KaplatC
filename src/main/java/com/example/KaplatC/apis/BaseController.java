package com.example.KaplatC.apis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class BaseController {
    private static final AtomicInteger reqCounter = new AtomicInteger(1);
    protected final Logger logger;

    public BaseController(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public int increaseReqCount() {
        return reqCounter.incrementAndGet();
    }

    public int getReqCounter() {
        return reqCounter.get();
    }
}
