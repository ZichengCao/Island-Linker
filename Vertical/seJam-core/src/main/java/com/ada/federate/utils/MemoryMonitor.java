package com.ada.federate.utils;

public class MemoryMonitor implements Runnable {
    private volatile boolean isRunning = true;

    public static double bytesToMegabytes(long bytes) {
        return (double) bytes / (1024 * 1024);
    }

    @Override
    public void run() {
        while (isRunning) {
            // 获取当前运行时实例
            record();

            try {
                // 线程休眠1秒
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void record() {
        // 获取当前运行时实例
        Runtime runtime = Runtime.getRuntime();

        // 获取堆内存使用情况
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        // 转换为兆字节（MB）
        double totalMemoryMB = bytesToMegabytes(totalMemory);
        double freeMemoryMB = bytesToMegabytes(freeMemory);
        double usedMemoryMB = bytesToMegabytes(usedMemory);

        // 打印堆内存使用情况
        LogUtils.debug(String.format("Memory Status: [total: %s MB, used: %s MB, free: %s MB]", totalMemoryMB, usedMemoryMB, freeMemoryMB));
    }

    public void stop() {
        isRunning = false;
    }

}
