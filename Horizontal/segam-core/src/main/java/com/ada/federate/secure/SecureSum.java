package com.ada.federate.secure;

import com.ada.federate.cache.ResultKVSet;
import com.ada.federate.rpc.RPCCommon;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecureSum {

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
            // arr[i] = 1;
            arr[i] = RandomUtils.nextInt(min, max + 1);
        }
        return arr;
    }

    /**
     * local encryption
     * <p>
     * f(x) = d + a_1*x + a_2*x^2 + ... . which a_i is random array
     *
     * @param n        silo size
     * @param localSum vi the local counting result of silo F_i
     * @param publicX  n different public parameters
     * @return [0, f(x_1), f(x_2), ... ]
     */
    public static List<Long> localEncrypt(List<Integer> publicX, long localSum, int n) {
        int[] a = generateRandomNum(1, 8, n - 1);

        // LogUtils.debug(String.format("random array: %s \n", Arrays.toString(a)));
        List<Long> fx = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int u = publicX.get(i);
            long temp = localSum;
            for (int k = 1; k < n; k++) {
                // FIXME: value may be overflow while n >= 10
                temp += a[k - 1] * (long) Math.pow(u, k);
                // temp += (long) Math.pow(u, k);
            }
            fx.add(temp);
        }
        return fx;
    }

    /**
     * f(x) = d + a_1*x + a_2*x^2 + ... . which a_i is random array
     *
     * @param a        Random array
     * @param x        x value
     * @param n        Number of participants
     * @param localSum Value to be encrypted
     * @return
     */
    public static double calcFx(int[] a, int x, int n, double localSum) {
        double temp = localSum;
        for (int k = 1; k < n; k++) {
            // NOTE: value may be overflow while n >= 10
            temp += a[k - 1] * (int) Math.pow(x, k);
        }
        return temp;
    }

    /**
     * Given a set of equal length arrays X and Y, return the value of the equation is obtained by Lagrangian interpolation.
     *
     * @param X arrays X
     * @param Y arrays Y
     * @param x given x
     * @return the value of the equation while given x.
     */
    public static long lagrangeInterpolation(long[] X, long[] Y, long x) {
        long n = Y.length;
        double y0;
        double L = 0;
        for (int i = 0; i < n; i++) {
            double k = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    k = k * (x - X[j]) / (double) (X[i] - X[j]);
                }
            }
            k = k * Y[i];
            L = L + k;
        }
        y0 = L;
        return (long) Math.round(y0);
    }

    public static void main(String[] args) {
        // // three data owners
        // int n = 3;
        // List<Integer> publicX = Arrays.asList(1, 2, 3);
        // // local encrypt
        // List<Integer> fxa = localEncrypt(publicX, -6, n);
        // List<Integer> fxb = localEncrypt(publicX, 0, n);
        // List<Integer> fxc = localEncrypt(publicX, 0, n);
        // System.out.println(fxa);
        // System.out.println(fxb);
        // System.out.println(fxc);
        // // secret sharing
        // List<Integer> fx1 = Arrays.asList(fxa.get(0), fxb.get(0), fxc.get(0));
        // List<Integer> fx2 = Arrays.asList(fxa.get(1), fxb.get(1), fxc.get(1));
        // List<Integer> fx3 = Arrays.asList(fxa.get(2), fxb.get(2), fxc.get(2));
        //
        // System.out.println(fx1);
        // System.out.println(fx2);
        // System.out.println(fx3);
        //
        // // local calc
        // int localSum1 = fx1.stream().mapToInt(Integer::intValue).sum();
        // int localSum2 = fx2.stream().mapToInt(Integer::intValue).sum();
        // int localSum3 = fx3.stream().mapToInt(Integer::intValue).sum();
        // System.out.println(localSum1 + " " + localSum2 + " " + localSum3);
        // int sum = lagrangeInterpolation(publicX.stream().mapToInt(Integer::intValue).toArray(), new int[]{localSum1, localSum2, localSum3}, 0);
        // System.out.println(sum);
    }
}
