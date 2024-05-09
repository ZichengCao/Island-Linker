package com.ada.federate.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ArrayUtils {

    public static boolean checkBoolArray(boolean[] booleans) {
        boolean flag = true;
        for (boolean bool : booleans) {
            flag &= bool;
        }
        return flag;
    }

    /**
     * 多个数组排列组合
     *
     * @param list 原始list
     * @param <T>  数据类型
     * @return
     */
    private static <T> List<List<T>> getDescartes(List<List<T>> list) {
        List<List<T>> returnList = new ArrayList<>();
        descartesRecursive(list, 0, returnList, new ArrayList<T>());
        return returnList;
    }

    /**
     * 递归实现
     * 原理：从原始list的0开始依次遍历到最后
     *
     * @param originalList 原始list
     * @param position     当前递归在原始list的position
     * @param returnList   返回结果
     * @param cacheList    临时保存的list
     */
    private static <T> void descartesRecursive(List<List<T>> originalList, int position, List<List<T>> returnList, List<T> cacheList) {
        List<T> originalItemList = originalList.get(position);
        for (int i = 0; i < originalItemList.size(); i++) {
            //最后一个复用cacheList，节省内存
            List<T> childCacheList = (i == originalItemList.size() - 1) ? cacheList : new ArrayList<>(cacheList);
            childCacheList.add(originalItemList.get(i));
            if (position == originalList.size() - 1) {//遍历到最后退出递归
                returnList.add(childCacheList);
                continue;
            }
            descartesRecursive(originalList, position + 1, returnList, childCacheList);
        }
    }

    /**
     * 多个数组排列组合
     *
     * @param list 原始list
     * @return
     */
    public static List<String> getPermutations(List<List<String>> list) {
        List<String> resultList = new ArrayList<>();
        List<List<String>> list1 = getDescartes(list);
        if (CollectionUtils.isNotEmpty(list1)) {
            list1.forEach(temp -> {
                String str = listToStr(temp);
                if (StringUtils.isNotEmpty(str)) {
                    resultList.add(listToStr(temp));
                }
            });
        }
        return resultList;
    }

    // public static List<String> getPermutations(Map<String, List<String>> listMap) {
    //     return getPermutations(new ArrayList<>(listMap.values()));
    // }

    public static String listToStr(List<String> list) {
        return String.join("_", list);
    }

    public static void main(String[] args) {
        List<String> list1 = Arrays.asList("一层", "二层", "三层");
        // List<String> list2 = Arrays.asList("圆形", "方形");
        ArrayList<List<String>> list3 = new ArrayList<>();
        list3.add(list1);
        // list3.add(list2);
        List<String> descartes = getPermutations(list3);
        descartes.forEach(System.out::println);
    }

}
