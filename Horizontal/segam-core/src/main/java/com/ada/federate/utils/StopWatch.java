package com.ada.federate.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class StopWatch {

    private static boolean isStart = false;
    public static LinkedHashMap<String, Long> timeframeList = new LinkedHashMap<>();
    private static long timestamp;

    public static void start() {
        isStart = true;
        timestamp = System.currentTimeMillis();
    }

    public static void addTimeRecord(String name) {
        if (!isStart) return;
        timeframeList.put(name, System.currentTimeMillis() - timestamp);
        timestamp = System.currentTimeMillis();
    }

    public static void stop() {
        isStart = false;
        timestamp = 0;
    }


    /**
     * Format the time difference for output
     */

    public static void formatPrintTestInfo(long startTimestamp, long endTimestamp) {
        long timestamp = endTimestamp - startTimestamp;
        System.out.printf("millisecond: %d ms. \n", timestamp);
        // System.out.printf("communication size: %d Bytes\n", TestApplication.communicationSize);
        // System.out.printf("second: %.3f s\n", timestamp / 1000.0);
    }

    /**
     * Format the time difference for output
     */

    public static void formatPrintTestInfo() {
        long total = 0L;
        for (long value : timeframeList.values()) {
            total += value;
        }
        timeframeList.put("total", total);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Long> entry : timeframeList.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            sb.append("<").append(key).append(":").append(value).append(">,");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        // System.out.println("Detail ( " + getCurrentTime() + " ): [" + sb + "]");
    }
}
