package com.example.KaplatC.apis;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class BaseController {
    private static final AtomicInteger reqCounter = new AtomicInteger(1);
    protected static final Logger logger = LoggerFactory.getLogger("com.example.KaplatC.request.logger");

    public int increaseReqCount() {
        return reqCounter.getAndIncrement();
    }

    public int getReqCounter() {
        return reqCounter.get();
    }

    public long writeLoggerInfo(String resource, String httpVerb) {
        long startTime = System.currentTimeMillis();
        logger.info("Incoming request #{} | resource: {} | HTTP Verb: {}", reqCounter.get(), resource, httpVerb);
        return startTime;
    }

    public void writeLoggerDebug(long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        logger.debug("request #{} | duration: {}ms", reqCounter.getAndIncrement(), duration);
    }
}
