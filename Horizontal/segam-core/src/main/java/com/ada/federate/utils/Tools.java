package com.ada.federate.utils;

import com.alibaba.fastjson2.JSONArray;
import org.apache.commons.lang3.RandomUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        LogUtils.info("Initialization begins, and each data owner negotiates the complete set...");
    }

    public static void printRpcHelloInfo(Map<String, Set<String>> fullKeySetMap) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("The negotiation of the complete set is completed, totaling %d attributes:", fullKeySetMap.size()));
        for (String key : fullKeySetMap.keySet()) {
            stringBuffer.append(String.format(" |%s|: %d", key, fullKeySetMap.get(key).size()));
        }
        LogUtils.info(stringBuffer.toString());
    }

    private static final DecimalFormat df2 = new DecimalFormat("0.00");
    private static final DecimalFormat df0 = new DecimalFormat("0.##");

    /**
     * Format floating-point type data, retaining two decimal places, and not displaying the decimal part if it is 0.
     *
     * @param num
     * @return
     */

    public static Object formatDoubleValue(Double num) {
        if (num == null) return null;
        num += 0.00001;
        String str = df2.format(num);
        if (str.endsWith("00")) return Integer.valueOf(df0.format(num));
        else return Double.valueOf(df0.format(num));
    }


    public static void main(String[] args) {
        List<String> list = Arrays.asList("cas", "vas", "gqw");
        String jsonStr = list2JsonString(list, String.class);
        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        System.out.println(jsonStr);
        System.out.println(jsonArray);
    }
}
