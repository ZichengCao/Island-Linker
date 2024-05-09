package com.ada.federate.utils;

import com.ada.federate.rpc.RPCCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ThreadTools {

    // simulate network latency
    public static long delay = 2;
    public static <T> List<T> runCallableTasks(ExecutorService executorService, List<Callable<T>> tasks) throws InterruptedException {
        Thread.sleep(delay);
        List<Future<T>> queryStatusList = executorService.invokeAll(tasks);
        List<T> resultList = new ArrayList<>();
        for (Future<T> statusFuture : queryStatusList) {
            try {
                resultList.add(statusFuture.get());
            } catch (ExecutionException e) {
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

        List<Future<?>> futureList = new ArrayList<>();
        for (Runnable task : tasks) {
            futureList.add(executorService.submit(task));
        }
        // FIXME 如果某个 silo 阻塞，全局都会阻塞
        for (Future<?> future : futureList) {
            future.get();
        }
    }

    public static void checkStatus(List<RPCCommon.Status> statusList) throws Exception {
        for (RPCCommon.Status status : statusList) {
            // 阻塞等待
            if (!status.getCode()) {
                throw new Exception(status.getMessage());
            }
        }
    }
}
