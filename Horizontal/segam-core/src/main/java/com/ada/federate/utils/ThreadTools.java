package com.ada.federate.utils;

import com.ada.federate.rpc.RPCCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ThreadTools {

    // simulate network latency (ms)
    public static long delay = 2;

    public static <T> List<T> runCallableTasks(ExecutorService executorService, List<Callable<T>> tasks) throws InterruptedException {
        // simulate transmission delay
        Thread.sleep(delay);
        // System.out.println(tasks);
        List<Future<T>> queryStatusList = executorService.invokeAll(tasks);

        List<T> resultList = new ArrayList<>();
        for (Future<T> statusFuture : queryStatusList) {
            // LogUtils.debug("isCompletedExceptionally: " + statusFuture.isCompletedExceptionally() + ". isDone: " + statusFuture.isDone() + ", isCancelled: " + statusFuture.isCancelled());
            try {
                resultList.add(statusFuture.get());
            } catch (Exception e) {
                LogUtils.error(LogUtils.buildErrorMessage(e));
                Throwable cause = e.getCause();
                if (cause != null) {
                    cause.printStackTrace();
                } else {
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    }

    public static void runRunnableTasks(ExecutorService executorService, List<Runnable> tasks) throws InterruptedException, ExecutionException {
        // simulate transmission delay
        Thread.sleep(delay);

        List<Future<?>> futureList = new ArrayList<>();
        for (Runnable task : tasks) {
            futureList.add(executorService.submit(task));
        }

        for (Future<?> future : futureList) {
            future.get();
        }
    }

    public static void checkStatus(List<RPCCommon.Status> statusList) throws Exception {
        for (RPCCommon.Status status : statusList) {

            if (!status.getCode()) {
                throw new Exception(status.getMessage());
            }
        }
    }
}
