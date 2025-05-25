package com.example.KaplatCalculatorApp.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class LogsService {

    public static Map<String, String> getLoggerLevel(String loggerName) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.exists(loggerName);
        Map<String, String> response = new HashMap<>();

        if (logger != null) {
            Level level = logger.getEffectiveLevel();
            response.put("Success", level.levelStr.toUpperCase());
        } else {
            response.put("Failure", "Logger Name: " + loggerName + ", was not found");
        }
        return response;
    }

    public static Map<String, String> setLoggerLevel(String loggerName, String loggerLevel) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.exists(loggerName);
        Map<String, String> response = new HashMap<>();
        boolean validLevel = isValidLevel(loggerLevel);

        if (logger != null && validLevel) {
            logger.setLevel(Level.valueOf(loggerLevel));
            response.put("Success", loggerLevel.toUpperCase());
        } else {
            String message = logger == null ? "Logger Name: " + loggerName + ", was not found" : "";
            message = validLevel ? message : message + " AND Logger Level: " + loggerLevel + ", Not a valid level for logger";
            response.put("Failure", message);
        }
        return response;
    }

    public static boolean isValidLevel(String level) {
        level = level.toUpperCase();
        return switch (level) {
            case "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF" -> true;
            default -> false;
        };
    }
}
