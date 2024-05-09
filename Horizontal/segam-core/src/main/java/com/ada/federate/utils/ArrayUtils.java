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
     * Recursive implementation for generating permutations and combinations of multiple arrays.
     *
     * @param list Original list
     * @param <T> Data type
     * @return
     */

    private static <T> List<List<T>> getDescartes(List<List<T>> list) {
        List<List<T>> returnList = new ArrayList<>();
        descartesRecursive(list, 0, returnList, new ArrayList<T>());
        return returnList;
    }

    private static <T> void descartesRecursive(List<List<T>> originalList, int position, List<List<T>> returnList, List<T> cacheList) {
        List<T> originalItemList = originalList.get(position);
        for (int i = 0; i < originalItemList.size(); i++) {
            List<T> childCacheList = (i == originalItemList.size() - 1) ? cacheList : new ArrayList<>(cacheList);
            childCacheList.add(originalItemList.get(i));
            if (position == originalList.size() - 1) {
                returnList.add(childCacheList);
                continue;
            }
            descartesRecursive(originalList, position + 1, returnList, childCacheList);
        }
    }

    /**
     * Permutation and combination of multiple arrays.
     *
     * @param list multiple list.
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

    public static String listToStr(List<String> list) {
        return String.join("_", list);
    }

}
