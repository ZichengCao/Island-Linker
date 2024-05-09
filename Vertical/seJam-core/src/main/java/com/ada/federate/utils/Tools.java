package com.ada.federate.utils;

import com.alibaba.fastjson2.JSONArray;
import org.apache.commons.lang3.RandomUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ada.federate.utils.ResultsUtils.getCurrentTime;

public class Tools {
    /**
     * generate random array
     *
     * @param min min value (inclusive)
     * @param max max value (inclusive)
     * @param n   array size
     */
    public static int[] generateRandomNum(int min, int max, int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = RandomUtils.nextInt(min, max + 1);
        }
        return arr;
    }

    /**
     * generate non-repetitive random array
     *
     * @param min min value (inclusive)
     * @param max max value (inclusive)
     * @param n   array size
     */
    public static int[] generateNonRepetitiveRandomNum(int min, int max, int n) {
        // List<Integer> list = new ArrayList<>();
        List<Integer> ret = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
        Collections.shuffle(ret);
        return (max - min < n) ? ret.stream().mapToInt(x -> x).toArray() :
                ret.subList(0, n + 1).stream().mapToInt(x -> x).toArray();
    }


    public static <T> String list2JsonString(Collection<T> list, Class<T> clazz) {
        if (clazz == String.class) {
            return list.stream().map(x -> "\"" + x + "\"").collect(Collectors.toList()).toString();
        } else if (clazz == Integer.class || clazz == Double.class) {
            return list.stream().map(Object::toString).collect(Collectors.toList()).toString();
        } else {
            return null;
        }
    }

    public static void printRpcHelloInfo() {
        LogUtils.info("初始化开始，协商全集……");
    }

    public static void printRpcHelloInfo(Map<String, Set<String>> fullKeySetMap) {
        LogUtils.info(String.format("协商全集完成，共 %d 个属性: ", fullKeySetMap.size()));
        for (String key : fullKeySetMap.keySet()) {
            LogUtils.info(String.format("|%s|: %d", key, fullKeySetMap.get(key).size()));
        }
    }

    private static final DecimalFormat df2 = new DecimalFormat("0.00");
    private static final DecimalFormat df0 = new DecimalFormat("0.##");

    /**
     * 格式化 float 类型数据，保留两位小数，小数部分为 0 不显示
     *
     * @param num
     * @return
     */
    public static Object formatDoubleValue(Double num) {
        if (num == null) return null;
        // java 采用银行家舍入制，加上一个很小的数，保证四舍五入
        num += 0.00001;
        String str = df2.format(num);
        if (str.endsWith("00")) return Integer.valueOf(df0.format(num));
        else return Double.valueOf(df0.format(num));
    }


    /**
     * l 数组 是否全部小于 r
     *
     * @param l
     * @param r
     * @return
     */
    public static boolean judge(int[] l, int[] r) {
        for (int i = 0; i < l.length; i++) {
            if (l[i] < r[i])
                return true;
        }
        return false;
    }


    /***
     * 格式化输出时间差
     */
    public static void formatPrintTestInfo(long startTimestamp, long endTimestamp) {
        long timestamp = endTimestamp - startTimestamp;
        System.out.printf("millisecond: %d ms. \n", timestamp);
        // System.out.printf("communication size: %d Bytes\n", TestApplication.communicationSize);
        // System.out.printf("second: %.3f s\n", timestamp / 1000.0);
    }

    /***
     * 格式化输出时间差
     */
    public static void formatPrintTestInfo(LinkedHashMap<String, Long> timeframeList) {

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
            sb.deleteCharAt(sb.length() - 1); // 删除末尾的逗号
        }
        System.out.println("Detail ( " + getCurrentTime() + " ): [" + sb + "]");
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("cas", "vas", "gqw");
        String jsonStr = list2JsonString(list, String.class);
        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        System.out.println(jsonStr);
        System.out.println(jsonArray);
    }
}
