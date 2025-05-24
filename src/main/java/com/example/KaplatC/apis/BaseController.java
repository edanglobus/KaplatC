package com.example.KaplatC.apis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public abstract class BaseController {
    private static Integer reqCount = 1;
    private final Logger logger;

    public BaseController(Class<T> child) {
        logger = LoggerFactory.getLogger(child);
    }

    public Integer increaseReqCount() {
        return ++reqCount;
    }
}
