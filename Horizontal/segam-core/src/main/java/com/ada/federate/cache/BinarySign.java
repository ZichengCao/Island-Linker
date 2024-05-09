package com.ada.federate.cache;

import java.util.*;

public class BinarySign {
    private List<String> unionSet;

    public List<Boolean> runEvenList;
    public List<Boolean> evenFlagList;
    private List<Long> l, r, threshold;
    public int size;

    public BinarySign(long boundS, long boundE, List<String> unionSet) {
        this.size = unionSet.size();
        evenFlagList = new ArrayList<>(Collections.nCopies(size, false));
        runEvenList = new ArrayList<>(Collections.nCopies(size, false));
        this.unionSet = new ArrayList<>(unionSet);
        threshold = new ArrayList<>(Collections.nCopies(size, 0L));

        l = new ArrayList<>(Collections.nCopies(size, boundS));
        r = new ArrayList<>(Collections.nCopies(size, boundE));
    }

    public void remove(int index) {
        l.remove(index);
        r.remove(index);
        threshold.remove(index);
        evenFlagList.remove(index);
        runEvenList.remove(index);
        unionSet.remove(index);
        size--;
    }

    public void remove(List<Integer> indices) {
        if (indices.size() == 0) return;

        if (indices.size() <= 20) {
            indices.sort(Collections.reverseOrder());
            for (int i = 0; i < indices.size(); i++) {
                int indexToRemove = indices.get(i);
                remove(indexToRemove);
            }
            return;
        }
        Set<Integer> indexSet = new HashSet<>(indices);
        List<String> newUnionSet = new ArrayList<>();
        List<Boolean> newEvenFlagList = new ArrayList<>();
        List<Boolean> newRunEvenList = new ArrayList<>();
        List<Long> newL = new ArrayList<>();
        List<Long> newR = new ArrayList<>();
        List<Long> newThreshold = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (!indexSet.contains(i)) {
                newUnionSet.add(unionSet.get(i));
                newEvenFlagList.add(evenFlagList.get(i));
                newRunEvenList.add(runEvenList.get(i));
                newL.add(l.get(i));
                newR.add(r.get(i));
                newThreshold.add(threshold.get(i));
            }
        }

        unionSet = newUnionSet;
        evenFlagList = newEvenFlagList;
        runEvenList = newRunEvenList;
        l = newL;
        r = newR;
        threshold = newThreshold;
        size = unionSet.size();
    }

    public Long l(int index) {
        return l.get(index);
    }

    public Long r(int index) {
        return r.get(index);
    }

    public Long threshold(int index) {
        return threshold.get(index);
    }

    public String unionSet(int index) {
        return unionSet.get(index);
    }


    public List<Long> getThreshold() {
        return threshold;
    }

    public List<String> getUnionSet() {
        return unionSet;
    }

    public String bound2String() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append("[").append(l(i)).append(",").append(r(i)).append("]");
            if (i != size - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void set_threshold(int index, long threshold) {
        this.threshold.set(index, threshold);
    }

    public void set_l(int index, long val) {
        this.l.set(index, val);
    }

    public void set_evenFlag(int index, boolean val) {
        this.evenFlagList.set(index, val);
    }

    public void set_runEven(int index, boolean val) {
        this.runEvenList.set(index, val);
    }


    public void set_r(int index, long val) {
        this.r.set(index, val);
    }


    public boolean judge() {
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) < r.get(i))
                return true;
        }
        return false;
    }
}