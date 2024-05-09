package com.ada.federate.utils;


import java.util.Objects;
import java.util.logging.Logger;

public class LogUtils {
    private static final Logger LOGGER = Logger.getLogger("logger");
    /**
     * -1:none,  0: console, 1: file
     */
    public static int STDOUT = 0;
    public static boolean DEBUG = false;

    public static void debug(Object msg) {
        if (STDOUT == 1) {
            LOGGER.info(findCaller() + ": " + msg);
        } else {
            System.out.println(findCaller() + ": " + msg);
        }
    }


    public static void info(String msg) {
        if (STDOUT == 1) {
            LOGGER.info(findCaller() + ": " + msg);
        } else
            System.out.println(findCaller() + ": " + msg);
    }


    public static void error(String msg) {
        if (STDOUT == 1)
            LOGGER.severe(findCaller() + ": " + msg);
        else
            System.out.println(findCaller() + ": " + msg);
    }

    private static String findCaller() {
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
        StackTraceElement caller = null;
        String logClassName = LogUtils.class.getName();
        int i = 0;
        for (int len = callStack.length; i < len; i++) {
            if (logClassName.equals(callStack[i].getClassName())) {
                break;
            }
        }
        caller = callStack[i + 2];
        // return "(" + caller.getClassName() + ":" + caller.getLineNumber() + ")";
        return caller.toString();
    }

    /***
     * build exception stack information
     * @param e
     * @return
     */
    public static String buildErrorMessage(Exception e) {
        String stackTrace = getStackTraceString(e);
        String exceptionType = e.toString();
        String exceptionMessage = e.getMessage();
        return String.format("%s: %s \r\n %s", exceptionType, exceptionMessage, stackTrace);
    }

    public static String getStackTraceString(Throwable ex) {//(Exception ex) {
        StackTraceElement[] traceElements = ex.getStackTrace();

        StringBuilder traceBuilder = new StringBuilder();

        if (traceElements != null && traceElements.length > 0) {
            for (StackTraceElement traceElement : traceElements) {
                traceBuilder.append("\t").append(traceElement.toString());
                traceBuilder.append("\n");
            }
        }
        return traceBuilder.toString();
    }
}