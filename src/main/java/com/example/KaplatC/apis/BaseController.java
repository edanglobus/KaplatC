package com.example.KaplatC.apis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class BaseController {
    private static final AtomicInteger reqCounter = new AtomicInteger(1);
    private static final Logger logger = LoggerFactory.getLogger("com.example.KaplatC.request.logger");


    public long writeLoggerInfo(String resource, String httpVerb) {
        long startTime = System.currentTimeMillis();
        logger.info("Incoming request #{} | resource: {} | HTTP Verb: {}", reqCounter.get(), resource, httpVerb);
        return startTime;
    }

    public void writeLoggerDebug(long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        logger.debug("request #{} | duration: {}ms", reqCounter.getAndIncrement(), duration);
    }

    public void writeLoggerWarn(String message) {
        reqCounter.getAndIncrement();
        logger.debug(message);
    }
}
